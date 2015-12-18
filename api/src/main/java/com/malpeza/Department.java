/*
 * Copyright (c) 2015, 2015, Malpeza and/or its affiliates. 
 * All rights reserved. Use is subject to license terms. 
 */

package com.malpeza;

public class Department {
  private int id;
  private String name;
  private String description;

  public Department() {

  }

  public Department(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public final int getId() {
    return id;
  }

  public final void setId(final int id) {
    this.id = id;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  public final String getDescription() {
    return description;
  }

  public final void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return String.format("%s(id: %d, name: %s, description: %s)%n", this.getClass().getSimpleName(), id, name,
        description);
  }
}
