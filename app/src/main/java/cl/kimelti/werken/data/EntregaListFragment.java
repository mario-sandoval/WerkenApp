package cl.kimelti.werken.data;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import cl.kimelti.werken.R;
import cl.kimelti.werken.data.model.EnvioVo;
import cl.kimelti.werken.data.model.RetiroVo;
import cl.kimelti.werken.databinding.FragmentEntregaListBinding;
import cl.kimelti.werken.databinding.EntregaListContentBinding;

import cl.kimelti.werken.data.placeholder.PlaceholderContent;
import cl.kimelti.werken.service.EnvioService;
import cl.kimelti.werken.service.RetiroService;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Entregas. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link EntregaDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EntregaListFragment extends Fragment {

    private List<EnvioVo> envioVoList;
    private SimpleItemRecyclerViewAdapter adapter;

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };

    private FragmentEntregaListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //envioVoList = new ArrayList<>();
        EnviosTask enviosTaskTask = new EnviosTask();
        try {
            synchronized(enviosTaskTask){
                enviosTaskTask.execute((Void) null).get();
            }
        } catch (Exception e) {
            Log.d("Error al obtener retiro", e.getMessage());
        }
        binding = FragmentEntregaListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);

        RecyclerView recyclerView = binding.entregaList;

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.entrega_detail_nav_container);

        /* Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        View.OnClickListener onClickListener = itemView -> {
            EnvioVo item =
                    (EnvioVo) itemView.getTag();
            Bundle arguments = new Bundle();
            arguments.putString(EntregaDetailFragment.ARG_ITEM_ID,String.valueOf(item.getId()));
            if (itemDetailFragmentContainer != null) {
                Navigation.findNavController(itemDetailFragmentContainer)
                        .navigate(R.id.fragment_entrega_detail, arguments);
            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_entrega_detail, arguments);
            }
        };

        /*
         * Context click listener to handle Right click events
         * from mice and trackpad input to provide a more native
         * experience on larger screen devices
         */
        View.OnContextClickListener onContextClickListener = itemView -> {
            EnvioVo item =
                    (EnvioVo) itemView.getTag();
            Toast.makeText(
                    itemView.getContext(),
                    "Context click of item " + item.getId(),
                    Toast.LENGTH_LONG
            ).show();
            return true;
        };

        //setHasOptionsMenu(true);
        //adapter = new SimpleItemRecyclerViewAdapter(envioVoList,onClickListener, onContextClickListener);
        recyclerView.setAdapter(adapter);
        setupRecyclerView(recyclerView, onClickListener, onContextClickListener);
    }


    private void setupRecyclerView(
            RecyclerView recyclerView,
            View.OnClickListener onClickListener,
            View.OnContextClickListener onContextClickListener
    ) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                envioVoList,
                onClickListener,
                onContextClickListener
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(queryListener);
        //MenuInflater menuInflater = getActivity().getMenuInflater();//.inflate(R.menu.main, menu);
        //menuInflater.inflate(R.menu.main, menu);

        //getting the search view from the menu
        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        //getting search manager from systemservice
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);

        //getting the search view
        final SearchView searchView = (SearchView) searchViewItem.getActionView();

        //you can put a hint for the search input field
        //searchView.setQueryHint(getString(R.string.search_hint));

        //assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        //by setting it true we are making it iconified
        //so the search input will show up after taping the search iconified
        //if you want to make it visible all the time make it false
        searchView.setIconifiedByDefault(true);

        //here we will get the search query

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("Search",query);
                List<EnvioVo> cajaList;
                adapter.getmValues().clear();
                if(query != null){
                    cajaList = EnvioService.getInstance().getEnviosByText(query);
                    adapter.getmValues().addAll(cajaList);
                }else{
                    adapter.getmValues().addAll(envioVoList);
                }
                adapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<EnvioVo> list;
                adapter.getmValues().clear();
                if(newText != null){
                    list = EnvioService.getInstance().getEnviosByText(newText);
                    adapter.getmValues().addAll(list);
                }else{
                    adapter.getmValues().addAll(envioVoList);
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        //return true;
    }
    */
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<EnvioVo> mValues;
        private final View.OnClickListener mOnClickListener;
        private final View.OnContextClickListener mOnContextClickListener;

        SimpleItemRecyclerViewAdapter(List<EnvioVo> items,
                                      View.OnClickListener onClickListener,
                                      View.OnContextClickListener onContextClickListener) {
            mValues = items;
            mOnClickListener = onClickListener;
            mOnContextClickListener = onContextClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            EntregaListContentBinding binding =
                    EntregaListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getNumeroTracking());
            holder.mContentView.setText(mValues.get(position).getNombreDestinatario());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.itemView.setOnContextClickListener(mOnContextClickListener);
            }
            holder.itemView.setOnLongClickListener(v -> {
                // Setting the item id as the clip data so that the drop target is able to
                // identify the id of the content
                ClipData.Item clipItem = new ClipData.Item(String.valueOf(mValues.get(position).getId()));
                ClipData dragData = new ClipData(
                        ((PlaceholderContent.PlaceholderItem) v.getTag()).content,
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                        clipItem
                );

                if (Build.VERSION.SDK_INT >= 24) {
                    v.startDragAndDrop(
                            dragData,
                            new View.DragShadowBuilder(v),
                            null,
                            0
                    );
                } else {
                    v.startDrag(
                            dragData,
                            new View.DragShadowBuilder(v),
                            null,
                            0
                    );
                }
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(EntregaListContentBinding binding) {
                super(binding.getRoot());
                mIdView = binding.idText;
                mContentView = binding.content;
            }

        }

        public void updateData(List<EnvioVo> viewModels) {
            mValues.clear();
            mValues.addAll(viewModels);
            notifyDataSetChanged();
        }

        public List<EnvioVo> getmValues(){
            return mValues;
        }
    }

    private class EnviosTask extends AsyncTask<Void,Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            envioVoList = EnvioService.getInstance().getEnviosByMensajero();
            return envioVoList != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                //adapter.updateData(envioVoList);
            }
        }
    }

    /*
    final private androidx.appcompat.widget.SearchView.OnQueryTextListener queryListener = new OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                getActivity().getActionBar().setSubtitle("List");
                grid_currentQuery = null;
            } else {
                getActivity().getActionBar().setSubtitle("List - Searching for: " + newText);
                grid_currentQuery = newText;

            }
            getLoaderManager().restartLoader(0, null, EntregaListFragment.this);
            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(getActivity(), "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
            return false;
        }
    };
    */
}