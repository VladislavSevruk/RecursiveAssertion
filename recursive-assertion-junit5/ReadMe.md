[![Build Status](https://travis-ci.org/VladislavSevruk/RecursiveAssertion.svg?branch=master)](https://travis-ci.com/VladislavSevruk/RecursiveAssertion)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_RecursiveAssertion&metric=alert_status)](https://sonarcloud.io/dashboard?id=VladislavSevruk_RecursiveAssertion)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=VladislavSevruk_RecursiveAssertion&metric=coverage)](https://sonarcloud.io/component_measures?id=VladislavSevruk_RecursiveAssertion&metric=coverage)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/recursive-assertion/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vladislavsevruk/recursive-assertion)

# Recursive assertion for JUnit 5
This utility library helps to assert complex models using various settings and provide convenient error messages using 
JUnit 5 AssertAll mechanism.

## Table of contents
* [Getting started](#getting-started)
  * [Maven](#maven)
  * [Gradle](#gradle)
* [Usage](#usage)
  * [Ignore null fields](#ignore-null-fields)
  * [Ignore fields by name](#ignore-fields-by-name)
  * [Ignore fields by path](#ignore-fields-by-path)
  * [Empty collection equals null](#empty-collection-equals-null)
  * [Sort collections](#sort-collections)
  * [Break on size inequality](#break-on-size-inequality)
  * [Break on id inequality](#break-on-id-inequality)
  * [Set custom model name](#set-custom-model-name)
* [Customization](#customization)
  * [Add custom verifier](#add-custom-verifier)
  * [Set custom identifier field for class](#set-custom-identifier-field-for-class)
  * [Set custom comparator for class](#set-custom-comparator-for-class)
* [License](#license)

## Getting started
To add library to your project perform next steps:

### Maven
Add the following dependency to your pom.xml:
```xml
<dependency>
      <groupId>com.github.vladislavsevruk</groupId>
      <artifactId>recursive-assertion-junit5</artifactId>
      <version>1.0.0</version>
</dependency>
```
### Gradle
Add the following dependency to your build.gradle:
```groovy
implementation 'com.github.vladislavsevruk:recursive-assertion-junit5:1.0.0'
```

## Usage
Let's assume that we have following model classes:
```kotlin
public class User {
    private Long id;
    private Contacts contacts;
    private List<Order> orders;

    // getters and setters
    ...
}

public class Contacts {
    private String email;
    private String phoneNumber;

    // getters and setters
    ...
}

public class Order {
    private Long id;
    private Boolean isDelivered;

    // getters and setters
    ...
}
```

To compare two models all you need to do is to call
```kotlin
User actual = getActualUser(); // some method for receiving actual value
User expected = getExpectedUser(); // some method for preparing expected value
RecursiveAssertion.assertThat(actual).isEqualTo(expected);
```

### Ignore null fields
Some of expected values cannot be predicted in advance (e.g. automatically generated id of new database item during 
parallel test run) but we don't want our object verification to fail because of some of fields at our expected model 
weren't set. You can ignore verifications for fields that have __null__ value at expected model using 
__ignoreNullFields__ method (default value is __false__):
```kotlin
User actual = new User();
actual.setContacts(new Contacts());

User expected = new User();

// verification won't be failed because of 'contacts' field has 'null' value at expected model
RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);

// verification will be failed
RecursiveAssertion.assertThat(actual).ignoreNullFields(false).isEqualTo(expected);

expected.setOrders(Collections.emptyList());
// verification will be failed because of 'orders' field has 'null' value at actual model
RecursiveAssertion.assertThat(actual).ignoreNullFields(true).isEqualTo(expected);
```

### Ignore fields by name
You can skip verifications of fields with specific name using __ignoreFields__ method:
```kotlin
User actual = new User();
actual.setId(1L);
Order actualOrder = new Order();
actualOrder.setId(2L);
actual.setOrders(Collections.singletonList(actualOrder));

User expected = new User();
Order expectedOrder = new Order();
expectedOrder.setId(3L);
expected.setOrders(Collections.singletonList(expectedOrder));

// verification won't be failed as 'id' fields validation 
// will be skipped for both 'User' and 'Order' models
RecursiveAssertion.assertThat(actual).ignoreFields("id").isEqualTo(expected);
```

### Ignore fields by path
If you want to skip verification for field with specific name but your model have nested object with field that have 
same name which you still want to verify you can ignore field by specific path using __ignoreFieldsByPath__ method:
```kotlin
User actual = new User();
actual.setId(1L);

User expected = new User();

// verification won't be failed
RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("User.id").isEqualTo(expected);

Order actualOrder = new Order();
actualOrder.setId(2L);
actual.setOrders(Collections.singletonList(actualOrder));

expected.setId(1L);
Order expectedOrder = new Order();
expectedOrder.setId(3L);
expected.setOrders(Collections.singletonList(expectedOrder));

// verification won't be failed
RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("User.orders.id").isEqualTo(expected);
// this verification will skip 'id' validation field only for 'order' with index '0'
RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("User.orders[0].id").isEqualTo(expected);

expected.setId(2L);
// verification will be failed because validation of 'id' field will be performed for 'User' model
RecursiveAssertion.assertThat(actual).ignoreFieldsByPath("User.orders.id").isEqualTo(expected);
```

### Empty collection equals null
In some cases it can be fine if actual model have one of _null_ or _empty collection_ values so you can specify that 
verification should treat both variants as one using __emptyCollectionEqualNull__ method (default value is __false__):
```kotlin
User actual = new User();
actual.setOrders(Collections.emptyList());

User expected = new User();

// verification won't be failed
RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(true).isEqualTo(expected);
// verification will be failed
RecursiveAssertion.assertThat(actual).emptyCollectionEqualNull(false).isEqualTo(expected);
```

### Sort collections
Working with collections sometimes you cannot guarantee order in which elements were received so you can sort 
collections before verification using __sortCollections__ method (default value is __false__):
```kotlin
User actual = new User();
Order actualOrder1 = new Order();
actualOrder1.setId(1L);
Order actualOrder2 = new Order();
actualOrder2.setId(2L);
actual.setOrders(Arrays.asList(actualOrder1, actualOrder2));

User expected = new User();
Order expectedOrder1 = new Order();
expectedOrder1.setId(1L);
Order expectedOrder2 = new Order();
expectedOrder2.setId(2L);
// reversed order
expected.setOrders(Arrays.asList(expectedOrder2, expectedOrder1));

// verification won't be failed
RecursiveAssertion.assertThat(actual).sortCollections(true).isEqualTo(expected);
// verification will be failed
RecursiveAssertion.assertThat(actual).sortCollections(false).isEqualTo(expected);
```
_NOTE:_ all collections for both actual and expected models will be sorted.

Please read [Set custom comparator for class](#set-custom-comparator-for-class) section for more details.

### Break on size inequality
While verifying arrays and iterables sometimes it make sense not to perform elements verifications if actual elements 
number doesn't equal to expected elements number (but there are cases when such verifications are still should be 
performed). For picking behavior that should be applied to your verification you can use __breakOnSizeInequality__ 
method (default value is __true__):
```kotlin
List<String> actual = Arrays.asList("value1", "value2");
List<String> expected = Arrays.asList("value1", "value2", "value3");

// Error message:
// org.opentest4j.AssertionFailedError: 
[[ArrayList] Size of actual and expected iterables differs] ==> expected: <3> but was: <2>
RecursiveAssertion.assertThat(actual).breakOnSizeInequality(true).isEqualTo(expected);

// Error message:
org.opentest4j.AssertionFailedError: 
[[ArrayList] Size of actual and expected iterables differs] ==> expected: <3> but was: <2>
org.opentest4j.AssertionFailedError: Missed element at 'ArrayList': value3
RecursiveAssertion.assertThat(actual).breakOnSizeInequality(false).isEqualTo(expected);
```

### Break on id inequality
While verifying arrays and iterables you can break verification of element if specified as id field value of expected 
model doesn't equal to value of actual model to not verify all fields of mismatched models. For this purpose you can 
use __breakOnIdInequality__ method (default value is __true__):
```kotlin
// add identifier field
AssertionContextManager.getContext().getIdentifierFieldStorage()
        .add(Order.class, Order.class.getDeclaredField("id"));

User actual = new User();
Order actualOrder = new Order();
actualOrder.setId(1L);
actualOrder.setIsDelivered(true);
actual.setOrders(Collections.singletonList(actualOrder));

User expected = new User();
Order expectedOrder = new Order();
expectedOrder.setId(2L);
expectedOrder.setIsDelivered(false);
expected.setOrders(Collections.singletonList(expectedOrder));

// Error message:
// org.opentest4j.AssertionFailedError: [User.orders[id=2].id] ==> expected: <1> but was: <2>
RecursiveAssertion.assertThat(actual).breakOnIdInequality(true).isEqualTo(expected);
// Error message:
// org.opentest4j.AssertionFailedError: [User.orders[id=2].id] ==> expected: <1> but was: <2>
// org.opentest4j.AssertionFailedError: [User.orders[id=2].isDelivered] ==> expected: <true> but was: <false>
RecursiveAssertion.assertThat(actual).breakOnIdInequality(false).isEqualTo(expected);
```

Please read [Set custom identifier field for class](#set-custom-identifier-field-for-class) section for more details.

### Set custom model name
By default, model class name is used for error messages and field paths generation but you can set custom model name 
for that purposes using __as__ method:
```kotlin
User actual = new User();
actual.setId(1L);

User expected = new User();

// Error message:
// org.opentest4j.AssertionFailedError: [User.id] ==> expected: <1> but was: <null>
RecursiveAssertion.assertThat(actual).isEqualTo(expected);
// Error message:
// org.opentest4j.AssertionFailedError: [currentUser.id] ==> expected: <1> but was: <null>
RecursiveAssertion.assertThat(actual).as("currentUser").isEqualTo(expected);
```
_NOTE:_ overridden name should be used for __ignoreFieldsByPath__ method patterns.

### Customization
### Add custom verifier
You can add your own verifier to customize verification of any element. Simply implement 
[FieldVerifier](../recursive-assertion-core/src/main/java/com/github/vladislavsevruk/assertion/verifier/FieldVerifier.java) 
interface and add it to 
[FieldVerifierStorage](../recursive-assertion-core/src/main/java/com/github/vladislavsevruk/assertion/storage/FieldVerifierStorage.java) 
from context (you can reach it calling ``AssertionContextManager.getContext().getFieldVerifierStorage()``).

### Set custom identifier field for class
You can specify identifier field for specific class model. That field will be used as identifier for 
[break on id inequality](#break-on-id-inequality) feature and field path generation for arrays and collections instead 
of element index for specified model class and its descendants. For that you need to add identifier field to 
[IdentifierFieldStorage](../recursive-assertion-core/src/main/java/com/github/vladislavsevruk/assertion/storage/IdentifierFieldStorage.java) 
from context (you can reach it calling ``AssertionContextManager.getContext().getIdentifierFieldStorage()``). By 
default, model doesn't have any identifier field.

### Set custom comparator for class
You can set specific comparator for any model class. Such comparator will be used for specified model class and its 
descendants. All you need to do is to add your Comparator to 
[ComparatorStorage](../recursive-assertion-core/src/main/java/com/github/vladislavsevruk/assertion/storage/ComparatorStorage.java) 
from context (you can reach it calling ``AssertionContextManager.getContext().getComparatorStorage()``). For models 
without specified custom comparator default hash code comparator will be used.

## License
This project is licensed under the MIT License, you can read the full text [here](LICENSE).