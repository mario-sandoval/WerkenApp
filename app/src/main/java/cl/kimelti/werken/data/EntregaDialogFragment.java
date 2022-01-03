package cl.kimelti.werken.data;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;
import cl.kimelti.werken.data.model.EnvioVo;
import cl.kimelti.werken.data.model.EstadoVo;
import cl.kimelti.werken.service.EstadoService;

public class EntregaDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface EntregaDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    EntregaDialogListener listener;

    private EnvioVo mItem;

    public EntregaDialogFragment(EnvioVo mItem){
        this.mItem = mItem;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (EntregaDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("This class must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View dialogEntregaView = inflater.inflate(R.layout.dialog_entrega, null);
        final Spinner estadoSpinner = dialogEntregaView.findViewById(R.id.estado_spin);
        //fill data in spinner
        ArrayAdapter<EstadoVo> adapter = new ArrayAdapter<>(App.getContext(), android.R.layout.simple_selectable_list_item, EstadoService.getInstance().getLocalEstados());
        estadoSpinner.setAdapter(adapter);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogEntregaView)
                // Add action buttons
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EstadoVo estado = (EstadoVo) estadoSpinner.getSelectedItem();
                        CheckBox checkTitular = dialogEntregaView.findViewById(R.id.checkTitular);
                        EditText recibidoPor = dialogEntregaView.findViewById(R.id.recibido_por);
                        EditText parentesco = dialogEntregaView.findViewById(R.id.parentesco);

                        mItem.setEstado(estado);
                        mItem.setRecibeTitular(checkTitular.isChecked());

                        if(estado.getCodigo() == 5){
                            mItem.setFechaEntrega(Calendar.getInstance().getTime());
                        }

                        if(recibidoPor != null){
                            mItem.setRecibidoPor(recibidoPor.getText().toString());
                        }

                        if(parentesco != null){
                            mItem.setParentesco(parentesco.getText().toString());
                        }

                        listener.onDialogPositiveClick(EntregaDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(EntregaDialogFragment.this);
                    }
                });
        return builder.create();
    }

    public EnvioVo getmItem(){
        return mItem;
    }
}
