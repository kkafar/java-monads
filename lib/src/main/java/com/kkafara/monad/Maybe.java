package com.kkafara.monad;

import com.kkafara.rt.Result;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class Maybe<T> {
  @Nullable
  private T value;

  private Maybe(@Nullable T value) {
    this.value = value;
  }

  public static <S> Maybe<S> of(@Nullable S value) {
    return new Maybe<>(value);
  }

  public static <S> Maybe<S> some(@Nullable S value) {
    assert value != null : "Value passed to some must not be null.";
    return Maybe.of(value);
  }

  public static <S> Maybe<S> none() {
    return Maybe.of(null);
  }

  public static <S> Maybe<S> wrap(@Nullable S value) {
    return new Maybe<>(value);
  }

  public T unwrap() {
    return value;
  }

  public Result<T, Void> unwrapToResult() {
    return value == null ? Result.err() : Result.ok(value);
  }

  public <R> Maybe<R> transform(Function<T, R> f) {
    return new Maybe<>(f.apply(value));
  }

  public Maybe<T> transformInPlace(Function<T, T> f) {
    value = f.apply(value);
    return this;
  }
}
