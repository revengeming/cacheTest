package com.sample.springboot.service;

import com.sample.springboot.entities.Employee;

import java.util.concurrent.CompletableFuture;

public interface Cache<K,V> {

  /**
   * Get Employee in cache by key
   * @param key
   */
  V get(K key);

  /**
   * Get Employee in cache by key in multi thread situation
   * @param key
   */
  CompletableFuture<V> getInMultiThread(K key);
}
