package com.example.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBadapter {
    static helperDB helper_DB ;
    public  DBadapter(Context context){
        helper_DB=new helperDB(context);
    };
    //Here I can Add Any business Method
    public long addEntery(phone_msg_detiles entry )
    {
        SQLiteDatabase db =helper_DB.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(helperDB.Phone_number,entry.getPhone());
        values.put(helperDB.Coi_Msg,entry.getMsg());
      long id =  db.insert(helperDB.Table_name,null,values);
      return  id ;//for the last row added

    }
    public phone_msg_detiles getentry()
    {
        phone_msg_detiles entry = null ;
        Cursor cr ;
        SQLiteDatabase db =helper_DB.getReadableDatabase();
        String[]clo={helperDB.Phone_number ,helperDB.Coi_Msg};
        cr=db.query(helperDB.Table_name,clo,null,null,null,null,null);
        while (cr.moveToNext())
        {
            entry=new phone_msg_detiles (cr.getString(0),cr.getString(1));
        }
        return entry ;
    }
    static class helperDB extends SQLiteOpenHelper {
        //Define her cause if i need change only will be from one place
        //tables name
        private static final int DBVersion =1 ;
        private static final String DB_Name = "My_DB" ;
        private static final String  Table_name= "MsgPhone" ;
        private static final String Col_ID = "_ID" ;
        private static final String Coi_Msg = "MSG" ;
        private static final String Phone_number = "Phone" ;

        private static final String CreatPhone_Msg_TABLE = "CREATE TABLE "+ Table_name+
         " ("+Col_ID + " 'INTEGER'PRIMARY KEY AUTOINCREMENT , "+ Phone_number +" 'TEXT ' ," +
                Coi_Msg +"'TEXT');";
        //any type should put in '' and all string in ""


      //default constractor
        public helperDB(Context context) {
            super(context, DB_Name, null,DBVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        db.execSQL(CreatPhone_Msg_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(sqLiteDatabase);
        }

    }
}

