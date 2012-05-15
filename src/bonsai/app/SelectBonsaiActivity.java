package bonsai.app;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SelectBonsaiActivity extends ListActivity {
	
    private static final int INSERT_ID = Menu.FIRST;
    
    // Utilidad de manejo de Base de Datos
	private BonsaiDbUtil bonsaidb;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectbonsai);
        bonsaidb = new BonsaiDbUtil(this);	// Construinos el DDBBAdapter
        bonsaidb.open();						// Abrimos la base de datos


        Cursor bonsaisCursor = bonsaidb.fetchAllBonsais();
        startManagingCursor(bonsaisCursor);
        String[] from = new String[]{BonsaiDbUtil.KEY_NAME};
        int[] to = new int[]{R.id.bonsairowtext};
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter bonsais = 
            new SimpleCursorAdapter(this, R.layout.bonsai_row, bonsaisCursor, from, to);
        setListAdapter(bonsais);
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        AndroidProjectActivity.bonsaiactual = id;
        
        try {
        	Cursor bonsai = bonsaidb.fetchBonsai(AndroidProjectActivity.bonsaiactual);
            startManagingCursor(bonsai);
            String nombre = bonsai.getString(
                    bonsai.getColumnIndexOrThrow(BonsaiDbUtil.KEY_NAME));
        	Toast.makeText(this, "El bonsai actual es: " + nombre, Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
        	Toast.makeText(this, "Error changing bonsai.", Toast.LENGTH_LONG).show();
        	
        }
        
        
    }
    
    public void goCreate(View v) {
    	AndroidProjectActivity.iamediting = false;
	    Intent editAct = new Intent().setClass(this, EditBonsaiActivity.class);
	    startActivity(editAct);
    }
    
    
    
    
}