/*
Copyright (c) 2013, Groupon, Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

Neither the name of GROUPON nor the names of its contributors may be
used to endorse or promote products derived from this software without
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.groupon.nakala.core;

import com.groupon.nakala.exceptions.ResourceInitializationException;
import com.groupon.util.io.IoUtil;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author npendar@groupon.com
 */
public abstract class AbstractRegExpFilter {
    protected List<Pattern> patterns;

    public void initialize(Class cls, String resource) throws ResourceInitializationException {
        try {
            initialize(IoUtil.readLines(IoUtil.read(cls, resource)));
        } catch (IOException e) {
            throw new ResourceInitializationException("Failed to load " + resource, e);
        }
    }

    public void initialize(String fileName) throws ResourceInitializationException {
        try {
            initialize(IoUtil.readLines(IoUtil.read(fileName)));
        } catch (IOException e) {
            throw new ResourceInitializationException("Failed to load " + fileName, e);
        }
    }

    public void initialize(Iterable<String> input) {
        patterns = new LinkedList<Pattern>();
        for (String line : input) {
            line = line.trim().toLowerCase().replace(" ", "\\W+").replace("-", "\\W+");
            patterns.add(Pattern.compile("\\b" + line + "\\b", Pattern.CASE_INSENSITIVE));
        }
    }
}