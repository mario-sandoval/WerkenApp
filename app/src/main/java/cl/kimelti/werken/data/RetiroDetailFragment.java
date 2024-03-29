package cl.kimelti.werken.data;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.concurrent.ExecutionException;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;
import cl.kimelti.werken.data.model.RetiroVo;
import cl.kimelti.werken.databinding.FragmentRetiroDetailBinding;
import cl.kimelti.werken.service.RetiroService;
import cl.kimelti.werken.ui.scanner.ScannerActivity;

/**
 * A fragment representing a single Retiro detail screen.
 * This fragment is either contained in a {@link RetiroListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class RetiroDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private RetiroVo mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = RetiroService.getInstance().getRetiroById(Integer.parseInt(clipDataItem.getText().toString()));
            updateContent();
        }
        return true;
    };
    private FragmentRetiroDetailBinding binding;

    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher, as an instance variable.
    private final ActivityResultLauncher<String> requestPermissionLauncher =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                startScanner();
            }
        }
        );

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RetiroDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            RetirosTask retirosTask = new RetirosTask(Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
            try {
                synchronized(retirosTask){
                    retirosTask.execute((Void) null).get();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRetiroDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.retiroDetail;

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScanner();
            }
        });

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (mItem != null) {
            mTextView.setText(mItem.toString());
            if (mToolbarLayout != null) {
                mToolbarLayout.setTitle(mItem.getEmpresa().getNombre());
            }
        }
    }

    private void openScanner(){
        if (ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA);
        }else{
            startScanner();
        }
    }

    private void startScanner(){
        Intent intent = new Intent(App.getContext(), ScannerActivity.class);
        startActivity(intent);
    }

    private class RetirosTask extends AsyncTask<Void,Void, Boolean> {

        private Integer retiroId;

        public RetirosTask(Integer retiroId){
            this.retiroId = retiroId;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            mItem = RetiroService.getInstance().getRetiroById(retiroId);
            return true;
        }
    }
}