/*!
 * Copyright 2018 Webdetails, a Hitachi Vantara company. All rights reserved.
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

package pt.webdetails.cda.utils.kettle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.table.TableModel;

import org.junit.Assert;
import org.junit.Test;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.value.ValueMetaInteger;
import org.pentaho.di.core.row.value.ValueMetaNumber;
import org.pentaho.di.core.row.value.ValueMetaString;

public class RowMetaToTableModelTest {

  @Test
  public void testConverter() {
    List<RowMetaAndData> origin = new ArrayList<>(2);
    RowMeta header = new RowMeta();
    header.addValueMeta( new ValueMetaString( "the string" ) );
    header.addValueMeta( new ValueMetaNumber( "the number" ) );
    header.addValueMeta( new ValueMetaInteger( "the integer" ) );
    origin.add( new RowMetaAndData( header, "first", 666d, 888L ) );
    origin.add( new RowMetaAndData( null, "second", 888, 666 ) );
    Function<List<RowMetaAndData>, TableModel> converter = RowMetaToTableModel.getConverter();
    TableModel tableModel = converter.apply( origin );
    Assert.assertEquals( "row nbr", 2, tableModel.getRowCount() );
    Assert.assertEquals( "col nbr", 3, tableModel.getColumnCount() );
    for ( int i = 0; i < 3; i++ ) {
      Assert.assertEquals( "column name #" + i, header.getFieldNames()[i], tableModel.getColumnName( i ) );
    }
    Assert.assertEquals( String.class, tableModel.getColumnClass( 0 ) );
    Assert.assertEquals( Double.class, tableModel.getColumnClass( 1 ) );
    Assert.assertEquals( Long.class, tableModel.getColumnClass( 2 ) );

    Assert.assertEquals( "second", tableModel.getValueAt( 1, 0 ) );
    Assert.assertEquals( 888, tableModel.getValueAt( 1, 1 ) );
    Assert.assertEquals( 888L, tableModel.getValueAt( 0, 2 ) );

    origin.clear();
    origin.add( new RowMetaAndData( null, "3rd", 1, 2 ) );
    origin.add( new RowMetaAndData( null, "4th", 3, 4 ) );

    TableModel tableModel2 = converter.apply( origin );
    Assert.assertEquals( "old header", "the number", tableModel2.getColumnName( 1 ) );
    Assert.assertEquals( "last value", 4, tableModel2.getValueAt( 1, 2 ) );
  }

}
