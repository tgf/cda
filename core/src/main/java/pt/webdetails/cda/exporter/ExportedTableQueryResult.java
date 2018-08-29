/*!
 * Copyright 2002 - 2017 Webdetails, a Hitachi Vantara company. All rights reserved.
 *
 * This software was developed by Webdetails and is provided under the terms
 * of the Mozilla Public License, Version 2.0, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */

package pt.webdetails.cda.exporter;

import java.io.OutputStream;

import javax.swing.table.TableModel;

public class ExportedTableQueryResult extends ExportedQueryResult {

  private TableModel table;
  private TableExporter exporter;

  public ExportedTableQueryResult( TableExporter exporter, TableModel table ) {
    super( exporter );
    this.table = table;
    this.exporter = exporter;
  }

  @Override
  public TableExporter getExporter() {
    return exporter;
  }

  public void writeOut( OutputStream out ) throws ExporterException {
    getExporter().export( out, table );
  }
}
