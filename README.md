# NEU repo parsing

A standalone library for parsing the [NEU repo](https://github.com/NotEnoughUpdates/NotEnoughUpdates-REPO/). Intended to
be used for NEU, but can also be used for other purposes.

## Versioning

Due to this library not having parity with the actual parser in the normal NEU mod, there will be rapid changes in
development. As such every commit could be a breaking change. Please plan accordingly for now.

## Licensing

This repository incorporates (in addition to libraries loaded by gradle) a file taken
from [the google gson extra repository](https://github.com/google/gson/blob/master/extras/src/main/java/com/google/gson/typeadapters/RuntimeTypeAdapterFactory.java)
with our own changes (highlighted in the code). That file is licensed under the Apache License version 2.0. The rest of
the source code is licensed under the BSD-2-Clause License. The `gradlew`, `gradlew.bat` and
`gradle/wrapper/gradle-wrapper.jar` are license under the Apache License version 2.0: https://docs.gradle.org/current/userguide/licenses.html.


## Usage

You will need to manually download the repository files. I would personally recommend making use of the E-Tag returned by
https://github.com/NotEnoughUpdates/NotEnoughUpdates-REPO/archive/refs/heads/master.zip for simple applications. This 
library is available at https://repo.nea.moe/#/releases/moe/nea/neurepoparser/1.0.0. Check out the 
[NEURepoParserTest.java](src/test/java/io/github/moulberry/repo/NEURepoParserTest.java)

