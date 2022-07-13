package com.kkafara.monad;

import com.kkafara.rt.Result;
import org.junit.Test;

import static org.junit.Assert.*;

public class MaybeTest {
  @Test
  public void wrapAndUnwrapNonNullValue() {
    Integer someValue = 5;
    Maybe<Integer> monad = Maybe.wrap(someValue);

    assertEquals(someValue, monad.unwrap());
  }

  @Test
  public void wrapAndUnwrapNullValue() {
    Integer noneValue = null;
    Maybe<Integer> monad = Maybe.wrap(noneValue);

    assertEquals(noneValue, monad.unwrap());
  }

  @Test
  public void transformNonNullValue() {
    Integer someValue = 5;
    Integer expectedResult = 31;
    Maybe<Integer> monad = Maybe.wrap(someValue);

    Integer result = monad
        .transform(value -> value * 2)
        .transform(value -> value * 3)
        .transform(value -> value + 1)
        .unwrap();

    assertEquals(expectedResult, result);
  }

  @Test
  public void transformBetweenTypes() {
    Integer someValue = 5;
    Maybe<Integer> monad = Maybe.wrap(someValue);

    Maybe<String> result = monad
        .transform(value -> value * value * value)
        .transform(Object::toString)
        .transform(value -> value.repeat(2));

    assertEquals("125".repeat(2), result.unwrap());
  }

  @Test
  public void transformInPlaceNonNullValue() {
    Integer someValue = 5;
    Integer expectedResult = 31;
    Maybe<Integer> monad = Maybe.wrap(someValue);

    monad
        .transformInPlace(value -> value * 2)
        .transformInPlace(value -> value * 3)
        .transformInPlace(value -> value + 1);

    assertEquals(expectedResult, monad.unwrap());
  }

  @Test
  public void transformAndUnwrapToResult() {
    Integer someValue = 5;
    String expectedResult = "31".repeat(2);
    Maybe<Integer> monad = Maybe.wrap(someValue);

    Result<String, Void> result = monad
        .transformInPlace(value -> value * 2)
        .transformInPlace(value -> value * 3)
        .transformInPlace(value -> value + 1)
        .transform(Object::toString)
        .transformInPlace(value -> value.repeat(2))
        .unwrapToResult();

    assertTrue(result.isOk());
    assertFalse(result.isOkValueNull());
    assertEquals(expectedResult, result.getOk());
  }
}
