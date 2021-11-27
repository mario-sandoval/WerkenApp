package cl.kimelti.werken.data;

import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.concurrent.ExecutionException;

import cl.kimelti.werken.R;
import cl.kimelti.werken.data.model.EnvioVo;
import cl.kimelti.werken.databinding.FragmentEntregaDetailBinding;
import cl.kimelti.werken.service.EnvioService;

/**
 * A fragment representing a single Entrega detail screen.
 * This fragment is either contained in a {@link EntregaListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class EntregaDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private EnvioVo mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = EnvioService.getInstance().getEnvioById(Integer.parseInt(clipDataItem.getText().toString()));
            updateContent();
        }
        return true;
    };
    private FragmentEntregaDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntregaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            EnvioTask envioTask = new EnvioTask(Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
            try {
                synchronized(envioTask){
                    envioTask.execute((Void) null).get();
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

        binding = FragmentEntregaDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.entregaDetail;

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
                mToolbarLayout.setTitle(mItem.getNumeroTracking());
            }
        }
    }

    private class EnvioTask extends AsyncTask<Void,Void, Boolean> {

        private Integer envioId;

        public EnvioTask(Integer mensajeroId){
            this.envioId = mensajeroId;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            mItem = EnvioService.getInstance().getEnvioById(envioId);
            return true;
        }
    }
}