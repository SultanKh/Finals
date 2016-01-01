#!/usr/bin/env python
# encoding: utf-8

"""
Copyright (c) 2010 The Echo Nest. All rights reserved.
Created by Tyler Williams on 2010-04-25.

Global configuration variables for accessing the Echo Nest web API.
"""

import pkg_resources

try:
    __version__ = pkg_resources.require("pyechonest")[0].version
except pkg_resources.DistributionNotFound:
    __version__ = "0.0.0"

import sys, os

envkeys = ["ZSLN415AFXNHTTRAS", "77a4929f094d2fc51cef227ab35079e6", "hKnFHMgnTFi35+zLMm3ibw"]
this_module = sys.modules[__name__]
for key in envkeys:
    setattr(this_module, key, os.environ.get(key, None))

API_HOST = 'developer.echonest.com'
"The API endpoint you're talking to"

API_SELECTOR = 'api'
"API selector... just 'api' for now"

API_VERSION = 'v4'
"Version of api to use... only 4 for now"

HTTP_USER_AGENT = 'PyEchonest'
"""
You may change this to be a user agent string of your
own choosing
"""

TRACE_API_CALLS = False
"""
If true, API calls will be traced to the console
"""

CALL_TIMEOUT = 10
"""
The API call timeout (seconds)
"""
