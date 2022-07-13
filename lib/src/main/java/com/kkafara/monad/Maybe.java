package com.kkafara.monad;

import com.kkafara.rt.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Maybe<T> {
  @Nullable
  private T value;

  private Maybe(@Nullable T value) {
    this.value = value;
  }

  @NotNull
  public static <S> Maybe<S> wrap(@Nullable S value) {
    return new Maybe<>(value);
  }

  @Nullable
  public T unwrap() {
    return value;
  }

  @NotNull
  public Result<T, Void> unwrapToResult() {
    return value == null ? Result.err() : Result.ok(value);
  }

  @NotNull
  public Result<T, T> unwrapToResultWithPredicate(Predicate<T> pred) {
    return pred.test(value) ? Result.ok(value) : Result.err(value);
  }

  @NotNull
  public <R> Maybe<R> transform(Function<T, R> f) {
    return new Maybe<>(f.apply(value));
  }

  @NotNull
  public <R> Maybe<R> transformNotNull(Function<T, R> f) {
    if (value == null) {
      return Maybe.wrap(null);
    }
    return new Maybe<>(f.apply(value));
  }

  @NotNull
  public Maybe<T> transformInPlace(Function<T, T> f) {
    value = f.apply(value);
    return this;
  }

  @NotNull
  public Maybe<T> transformNotNullInPlace(Function<T, T> f) {
    if (value != null) {
      value = f.apply(value);
    }
    return this;
  }

  @NotNull
  public Maybe<T> defaultValueIfNullInPlace(@Nullable T defaultValue) {
    if (value == null) {
      value = defaultValue;
    }
    return this;
  }

  @NotNull
  public Maybe<T> ifFailureInPlace(Predicate<T> pred, UnaryOperator<T> f) {
    if (pred.test(value)) {
      return Maybe.wrap(f.apply(value));
    }
    return this;
  }
}
