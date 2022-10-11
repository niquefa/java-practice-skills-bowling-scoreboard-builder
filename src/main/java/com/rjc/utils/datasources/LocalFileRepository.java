package com.rjc.utils.datasources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rjc.exceptions.RJCException;
import com.rjc.utils.RJCUtils;

public class LocalFileRepository implements DataRepository {

  private String fileName;
  private List<String> data;

  public LocalFileRepository() {
  }

  public LocalFileRepository(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public List<String> getData() {
    if (this.fileName == null || this.data == null) {
      return new ArrayList<>();
    }
    return this.data;
  }

  @Override
  public void create() throws IOException, IllegalArgumentException, RJCException {
    if (this.data != null) {
      return;
    }

    this.data = RJCUtils.readFromFileLineByLine(this.fileName);
  }

  @Override
  public String getSource() {
    return this.fileName;
  }

}
