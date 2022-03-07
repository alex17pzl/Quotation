package com.example.fragments;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.CustomRecyclerAdapter;
import com.example.databases.DataBase;
import com.example.databases.QuotationDAO;
import com.example.dialogs.CustomDialogFragment;
import com.example.pojo.Quotation;
import com.example.quotation.R;
import com.example.threads.OneThread;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    CustomRecyclerAdapter adapter;
    boolean removeAllVisible;

    private QuotationDAO quotationDAO;
    OneThread thread;

    CoordinatorLayout coordinatorLayout;

    public FavouriteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getChildFragmentManager().setFragmentResultListener(
                "remove_all", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                quotationDAO.deleteAllQuotation();
                            }
                        }).start();

                        adapter.deleteAllQuotations();

                        removeAllVisible = false;

                        // Se vuelve a llamar al método onCreateOptionsMenu()
                        getActivity().invalidateOptionsMenu();
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, null);

        quotationDAO = quotationDAO = DataBase.getInstance(requireContext()).obtainInterface();

        coordinatorLayout = view.findViewById(R.id.coordinatorFavourite);

        RecyclerView recycler = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(requireContext(), 1);
        recycler.setLayoutManager(manager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Quotation quotation = adapter.getQuotation(viewHolder.getLayoutPosition());
                int position = viewHolder.getLayoutPosition();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        quotationDAO.deleteQuotation(quotation);
                    }
                }).start();

                adapter.deleteItem(position);

                if (adapter.getItemCount() == 0) {
                    removeAllVisible = false;
                } else {
                    removeAllVisible = true;
                }

                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Quotation eliminada", BaseTransientBottomBar.LENGTH_SHORT).setAction("Deshacer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                quotationDAO.addQuotation(quotation);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        removeAllVisible = true;
                                        getActivity().invalidateOptionsMenu();
                                    }
                                });
                            }
                        }).start();
                        adapter.addQuoteAtGivenPosition(quotation, position);
                    }
                });

                snackbar.show();

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });

        itemTouchHelper.attachToRecyclerView(recycler);

        adapter = new CustomRecyclerAdapter(new ArrayList<Quotation>(), new CustomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quotation quotation) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String autor = quotation.getQuoteAuthor();

                try {
                    // Para que esté codificado en UTF-8
                    autor = URLEncoder.encode(autor, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (autor.equals("") || autor.equals(null)) {
                    //Toast.makeText(requireContext(), "No es posible obtener la información del autor", Toast.LENGTH_SHORT).show();
                    Snackbar.make(coordinatorLayout, "Autor anónimo - No es posible realizar la búsqueda", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else {
                    intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + autor));
                    //intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Albert_Einstein"));

                    // Get the list of Activities able to manage that Intent
                    List<ResolveInfo> activities =
                            requireContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (activities.size() > 0) {
                        startActivity(intent);
                    }
                }
            }
        });

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(requireContext(), 1);
        recycler.addItemDecoration(divider);

        recycler.setAdapter(adapter);

        return view;
    }

    public List<Quotation> getMockQuotations() {
        List<Quotation> quotations = new ArrayList<>();

        Quotation q1 = new Quotation("Cita 1", "Pablo Alborán");
        Quotation q2 = new Quotation("Cita 2", "Sebastian Yatra");
        Quotation q3 = new Quotation("Cita 3", "Joaquín Sabina");
        Quotation q4 = new Quotation("Cita 4", "Miguel de Cervantes");
        Quotation q5 = new Quotation("Cita 5", "Gabriel García Márquez");
        Quotation q6 = new Quotation("Cita 6", "Paloma Sánchez-Garnica");
        Quotation q7 = new Quotation("Cita 7", "Julio Verne");
        Quotation q8 = new Quotation("Cita 8", "Federico García Lorca");
        Quotation q9 = new Quotation("Cita 9", "Frank McCourt");
        Quotation q10 = new Quotation("Cita 10", "David Bisbal");
        Quotation q11 = new Quotation("Cita 11", "");

        quotations.add(q1);
        quotations.add(q2);
        quotations.add(q3);
        quotations.add(q4);
        quotations.add(q5);
        quotations.add(q6);
        quotations.add(q7);
        quotations.add(q8);
        quotations.add(q9);
        quotations.add(q10);
        quotations.add(q11);

        return quotations;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_favourite_activity, menu);
        menu.findItem(R.id.remove_all_quotes).setVisible(removeAllVisible);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.remove_all_quotes) {
            (new CustomDialogFragment()).show(getChildFragmentManager(), null);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void showDeleteAllQuotationsOption(List<Quotation> quotes) {
        // Si hay alguna cita favorita muestra la opcíon de borrar todas las citas
        if (quotes.size() != 0) {
            adapter.deleteAllQuotations();
            adapter.addFavouriteQuotationsToStoredList(quotes);
            removeAllVisible = true;
        } else {
            removeAllVisible = false;
        }
        // Se vuelve a llamar al método onCreateOptionsMenu()
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onResume() {
        super.onResume();
        thread = new OneThread(this);
        thread.start();
    }
}