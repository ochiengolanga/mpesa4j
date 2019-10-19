# How to Build and Contribute
Below are guidelines for building and code contribution.

## Prerequisites
- JDK 11 and above
- [Gradle](https://gradle.org/) 5.0 and above

## Build from source
To build the project, run maven commands.

```bash
git clone https://github.com/ochiengolanga/mpesa4j.git 
cd mpesa4j
gradle clean assemble
```

*Cloning the git repository on Windows*

Some files in the git repository may exceed the Windows maximum file path (260 characters), depending on where you clone the repository. If you get `Filename too long` errors, set the `core.longPaths=true` git option:

```
git clone -c core.longPaths=true https://github.com/ochiengolanga/mpesa4j.git
cd mpesa4j
gradle clean assemble
```

## Test

- Run unit tests

```bash
gradle clean test
```

- Skip test execution

```bash
gradle clean build -x test
```

## Version management
Developing version naming convention is like `2.0.0-SNAPSHOT`. Release version naming convention is like `2.0.0`. Please don't update version if no release plan. 

## CI

[CircleCi](https://circleci.com/gh/ochiengolanga/mpesa4j) is enabled.

## Contribution
Code contribution is welcome. To contribute to existing code or add a new starter, please make sure below check list is checked.
- [ ] Documents are updated to align with code.
- [ ] New starter must have sample folder containing sample code and corresponding readme file.
- [ ] Code coverage for new code >= 65%. Code coverage check is enabled with 65% bar.

