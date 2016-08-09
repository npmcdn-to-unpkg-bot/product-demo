package com.aytov.facade.dto;

import java.util.List;

public class BaseListDto<T extends Transferable> implements Transferable{
  
  private Long count;
  private List<T> list;

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
    this.count = Long.valueOf(list.size());
  }

  public Long getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "BaseListDto{" +
            "count=" + count +
            ", list=" + list +
            '}';
  }
}