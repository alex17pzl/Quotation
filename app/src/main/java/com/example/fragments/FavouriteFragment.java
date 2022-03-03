package com.example.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.adapter.CustomRecyclerAdapter;
import com.example.databases.DataBase;
import com.example.databases.QuotationDAO;
import com.example.dialogs.CustomDialogFragment;
import com.example.pojo.Quotation;
import com.example.quotation.R;
import com.example.threads.OneThread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    CustomRecyclerAdapter adapter;
    boolean removeAllVisible;

    private QuotationDAO quotationDAO;
    OneThread thread;

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

                        removeAllVisible =  false;

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

        RecyclerView recycler = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(requireContext(), 1);
        recycler.setLayoutManager(manager);

        //ItemTouchHelper itemTouchHelper = ;

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

                if (autor == null || autor == "") {
                    Toast.makeText(requireContext(), "No es posible obtener la información del autor", Toast.LENGTH_SHORT).show();
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
        }, new CustomRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage(getString(R.string.deleteItem));

                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                quotationDAO.deleteQuotation(adapter.getQuotation(position));
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.deleteItem(position);

                                        if (adapter.getItemCount() == 0) {
                                            removeAllVisible = false;
                                        } else {
                                            removeAllVisible = true;
                                        }
                                    }
                                });
                            }
                        }).start();

                        // Se vuelve a llamar al método onCreateOptionsMenu()
                        getActivity().invalidateOptionsMenu();
                    }
                });

                builder.setNegativeButton(getString(R.string.no), null);

                builder.create().show();
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