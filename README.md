# CI Server

TODO write this.

The project is a Intellij project using Java 13 and contains a jUnit test suite.

## How to run the tests from the terminal
First `junit-platform-console-standalone` needs to be downloaded from [here](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.6.0/junit-platform-console-standalone-1.6.0.jar). The project needs atleast Java 10, but Java 13 is recommended.
Then the tests can be run as follows in a shell from the root of the repository.

```bash
$ mkdir bin
$ javac -d bin -cp junit-platform-console-standalone-1.6.0.jar {src,tests}/**/*.java
$ java -jar junit-platform-console-standalone-1.6.0.jar --disable-banner -cp bin --scan-classpath
╷
├─ JUnit Jupiter ✔
└─ JUnit Vintage ✔

Test run finished after 0 ms
[         2 containers found      ]
[         0 containers skipped    ]
[         2 containers started    ]
[         0 containers aborted    ]
[         2 containers successful ]
[         0 containers failed     ]
[         0 tests found           ]
[         0 tests skipped         ]
[         0 tests started         ]
[         0 tests aborted         ]
[         0 tests successful      ]
[         0 tests failed          ]
```

### Contributions

TODO

