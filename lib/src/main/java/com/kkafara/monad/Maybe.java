package com.kkafara.monad;

import com.kkafara.rt.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

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
  public <R> Maybe<R> transform(Function<T, R> f) {
    return new Maybe<>(f.apply(value));
  }

  @NotNull
  public Maybe<T> transformInPlace(Function<T, T> f) {
    value = f.apply(value);
    return this;
  }
}
