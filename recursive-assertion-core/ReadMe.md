[![Build Status](https://travis-ci.org/VladislavSevruk/RecursiveAssertion.svg?branch=master)](https://travis-ci.com/VladislavSevruk/RecursiveAssertion)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_RecursiveAssertion&metric=alert_status)](https://sonarcloud.io/dashboard?id=VladislavSevruk_RecursiveAssertion)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_RecursiveAssertion&metric=coverage)](https://sonarcloud.io/component_measures?id=VladislavSevruk_RecursiveAssertion&metric=coverage)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/recursive-assertion/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/recursive-assertion)

# Recursive assertion core
Core contains common assertion mechanism for all testing libraries.  
For more detailed description of features for certain library please follow to corresponded module:
* [Recursive assertion for AssertJ](../recursive-assertion-assertj)
* [Recursive assertion for JUnit 5](../recursive-assertion-junit5)
* [Recursive assertion for TestNG](../recursive-assertion-testng)

## Table of contents
* [Getting started](#getting-started)
  * [Maven](#maven)
  * [Gradle](#gradle)
* [Another testing library integration](#another-testing-library-integration)
* [License](#license)

## Getting started
To add library to your project perform next steps:

### Maven
Add the following dependency to your pom.xml:
```xml
<dependency>
      <groupId>com.github.vladislavsevruk</groupId>
      <artifactId>recursive-assertion-core</artifactId>
      <version>1.0.0</version>
</dependency>
```
### Gradle
Add the following dependency to your build.gradle:
```groovy
implementation 'com.github.vladislavsevruk:recursive-assertion-core:1.0.0'
```

## Another testing library integration
To integrate with another testing library all you have to do is to implement 
[CommonSoftAssertion](src/main/java/com/github/vladislavsevruk/assertion/verifier/CommonSoftAssertion.java) interface 
and extend [AbstractRecursiveAssertion](src/main/java/com/github/vladislavsevruk/assertion/AbstractRecursiveAssertion.java)
using interface implementation to override it's _useCommonSoftAssertion_ abstract method.

## License
This project is licensed under the MIT License, you can read the full text [here](LICENSE).