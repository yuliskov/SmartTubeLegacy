#!/usr/bin/env python2.7

import json, sys
import urllib2
import rfc822, datetime, time
import os

import argparse

DOWNLOAD_URL = 'downloadUrl'
PACKAGE = 'package'
VERSION_CODE = 'versionCode'
CHANGELOG = 'changelog'

APK_CONTENT_TYPE = 'application/vnd.android.package-archive'

class HeadRequest(urllib2.Request):
    def get_method(self):
        return "HEAD"

class VersionListException(Exception):
    pass

class VersionList:
    """AppUpdateChecker JSON file generator / updater

    This creates and updates the JSON file read by AppUpdateChecker, as well as allows
    a file to be verified to ensure that it's constructed properly.
    """
    versions = None
    package = None

    def __init__(self, json_file=None):
        self.json_file = json_file
        if json_file:
            self.json = json.load(json_file)
            self.parse()

    def parse(self):
        if not self.json_file:
            return

        if type(self.json) != dict:
            return
        self.versions = dict(self.json)
        if PACKAGE in self.versions:
            self.package = self.versions[PACKAGE]
            del self.versions[PACKAGE]

    def verify(self, online=False):
        """Returns a tuple of verification, error message
        
        Raises VersionListException if the list hasn't been loaded"""

        if not self.json_file:
            raise VersionListException("must load a version list file first")

        ver = self.versions
        if type(ver) != dict:
            return (False,"Document is not a JSON object")

        if not self.package:
            return (False,"missing %s key" % PACKAGE)
        if DOWNLOAD_URL not in self.package:
            return (False,"missing %s key in %s object" % (DOWNLOAD_URL, PACKAGE))
        
        for code, info in ver.iteritems():
            if type(info) != dict:
                return (False,"value for version '%s' is not a JSON object" % code)
            if type(ver[code][VERSION_CODE]) != int:
                return (False,"version code in key %s of version '%s' is not an int" % (VERSION_CODE, code))
            if type(ver[code][CHANGELOG]) != list:
                return (False, "key %s in version '%s' is not a list" % (CHANGELOG, code))

        if online:
            return self.verify_online()

        return (True, None)

    def download_url(self, url=None):
        if url:
            if not self.package:
                self.package = {}
            self.package[DOWNLOAD_URL] = url

        return self.package[DOWNLOAD_URL]

    def version_latest(self):
        ver = self.versions_sorted()[-1]
        return (ver, self.versions[ver])

    def versions_sorted(self):
        """Retrieves a sorted list of all the version names, sorted by version code
        
        Raises VersionListException if the list hasn't been loaded"""
        if not self.json_file:
            raise VersionListException("must load a version list file first")

        return sorted(self.versions, key=lambda ver: self.versions[ver][VERSION_CODE])

    def verify_online(self):
        url = self.download_url()

        try:
            res = urllib2.urlopen(HeadRequest(url))
        except urllib2.HTTPError as e:
            return (False, e)

        if res.code != 200:
            return (False, "%d %s" % (res.code, res.msg))

        sys.stderr.writelines("HEAD %s returned %d %s\n" % (url, res.code, res.msg))

        content_type = res.headers['content-type']
        if APK_CONTENT_TYPE != content_type:
            sys.stderr.writelines("warning: content type returned by %s should be %s, not %s\n" % (url, APK_CONTENT_TYPE, content_type))

        last_modified = res.headers.get('last-modified', None)
        if last_modified:
            last_modified = datetime.datetime.fromtimestamp(time.mktime(rfc822.parsedate(last_modified)))
        sys.stderr.writelines("last modified %s\n" % last_modified)

        size = res.headers.get('content-length', None)
        if size and size < 4000:
            return (False, "content length of %s was less than 4k." % url)

        res.close()

        return (True, None)

    def write_json(self, out):
        j = dict(self.versions)
        j[PACKAGE] = self.package
        json.dump(j, out, indent=2)

    def add_release(self, ver_code, ver_name, changelog=[]):
        if not self.versions:
            self.versions = {}
        if ver_name in self.versions.keys():
            raise VersionListException("version '%s' already exists" % ver_name)
        ver_code = int(ver_code)
        if ver_code in map(lambda v: v[VERSION_CODE], self.versions.values()):
            raise VersionListException("version code '%d' already exists" % ver_code)
        self.versions[ver_name] = {VERSION_CODE: ver_code, CHANGELOG: changelog}

def release_cmd(args):
    if args.filename:
        try:
            inpt = open(args.filename)
        except IOError as e:
            # this is not ideal according to http://docs.python.org/howto/doanddont.html
            # but there's no good way to determine if it's a "file not found" or other error
            # based on the exception alone
            if not os.path.exists(args.filename):
                inpt = None
            else:
                raise e

        ver = VersionList(inpt)
        if inpt:
            ver.verify()
    else:
        ver = VersionList(sys.stdin)
        ver.verify()

    try:
        ver.add_release(args.code, args.name, args.changelog)
        if args.url:
            ver.download_url(args.url)
        if args.filename:
            out = open(args.filename, 'w')
        else:
            out = sys.stdout
        ver.write_json(out)
    except VersionListException as e:
        sys.stderr.writelines("%s\n" % e)

def verify_cmd(args):
    if args.filename:
        inpt = open(args.filename)
    else:
        inpt = sys.stdin
    ver = VersionList(inpt)
    (res, err) = ver.verify(online=args.online)
    if res:
        print "verification succeeded: no errors found"
        latest, latest_info = ver.version_latest()
        print "Latest version is %s (%d)" % (latest, latest_info[VERSION_CODE])
    else:
        print "verification failed: %s" % err

if __name__=='__main__':
    parser = argparse.ArgumentParser(
            description="Generate or update a static json AppUpdateChecker file.")

    parser.add_argument("-f", "--file", dest="filename",
            help="read/write version information to FILE", metavar="FILE")

    subparsers = parser.add_subparsers(help='sub-command help')

    verify = subparsers.add_parser('verify', help="verify the document")
    release = subparsers.add_parser('release', help="add a new release")

    verify.add_argument("-d", "--verify-download", dest="online", action="store_true",
            help="when verifying, perform a HEAD request on the download URL to ensure it is valid")

    verify.set_defaults(func=verify_cmd)

    release.add_argument("-u", "--url", dest="url", help="set/update download URL")

    release.add_argument('code', help='integer version code')
    release.add_argument('name', help='a unique name for the release')
    release.add_argument('changelog', help='changelog entries', nargs='+')
    release.set_defaults(func=release_cmd)

    args = parser.parse_args()
    args.func(args)
