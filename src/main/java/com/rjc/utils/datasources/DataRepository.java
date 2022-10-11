package com.rjc.utils.datasources;

import java.io.IOException;
import java.util.List;

import com.rjc.exceptions.RJCException;

public interface DataRepository {

  /**
   * Gets application data from the data repository
   *
   * @return <code>ArrayList</code> of <code>String</code> values.
   * @throws IOException
   */
  public List<String> getData();

  /**
   * Returns the identifier of the data source
   *
   * @return <code>String</code>.
   */
  public String getSource();

  /**
   * Create the given Data repository given the creation arguments.
   *
   * @param <code>String<code> creationArguments: the creation arguments needed to
   *                           create the repository
   * @throws IOException
   * @throws RJCException
   * @throws IllegalArgumentException
   */
  public void create() throws IOException, IllegalArgumentException, RJCException;
}
