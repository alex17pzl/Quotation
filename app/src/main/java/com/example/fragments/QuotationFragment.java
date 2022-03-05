package com.example.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.DataBase;
import com.example.databases.QuotationDAO;
import com.example.pojo.Quotation;
import com.example.quotation.R;
import com.example.threads.QuotationThread;
import com.example.webservice.RetrofitInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuotationFragment extends Fragment {

    //private int quotesReceived = 0;
    Menu thisMenu;
    boolean addVisible;
    TextView tvDentroSv;
    TextView sampleText;

    private QuotationDAO quotationDAO;
    QuotationThread thread;

    View view;

    Retrofit retrofit;
    RetrofitInterface retrofitInterface;
    SwipeRefreshLayout swipeRefreshLayout;

    CoordinatorLayout coordinatorLayout;

    public QuotationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quotation, null);

        tvDentroSv = view.findViewById(R.id.tvDentroSv);
        sampleText = view.findViewById(R.id.sampleText);

        coordinatorLayout = view.findViewById(R.id.coordinatorQuotation);

        quotationDAO = DataBase.getInstance(requireContext()).obtainInterface();

        // Se crea una instancia de Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.forismatic.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Se crea la implementación de la interfaz previamente definida
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingFavourite);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVisible = false;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        quotationDAO.addQuotation(new Quotation(tvDentroSv.getText().toString(), sampleText.getText().toString()));
                    }
                }).start();

                // Se vuelve a llamar al método onCreateOptionsMenu()
                getActivity().invalidateOptionsMenu();
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                String request = prefs.getString("requests", "");

                hideAllActionBarOptionAndShowProgressBar();

                String req = prefs.getString("languages", "");

                if (isConnected()) {
                    if (request.equals("get")) {

                        Call<Quotation> call = retrofitInterface.getQuotation(req);

                        call.enqueue(new Callback<Quotation>() {
                            @Override
                            public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                                updateQuoteTextAndQuoteAuthor(response.body());
                            }

                            @Override
                            public void onFailure(Call<Quotation> call, Throwable t) {
                                updateQuoteTextAndQuoteAuthor(null);
                            }
                        });

                    } else {
                        Call<Quotation> call = retrofitInterface.postQuotation("getQuote", "json", req);

                        call.enqueue(new Callback<Quotation>() {
                            @Override
                            public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                                updateQuoteTextAndQuoteAuthor(response.body());
                            }

                            @Override
                            public void onFailure(Call<Quotation> call, Throwable t) {
                                updateQuoteTextAndQuoteAuthor(null);
                            }
                        });
                    }
                } else {
                    //Toast.makeText(requireContext(), "There is no connection", Toast.LENGTH_SHORT).show();
                    Snackbar.make(coordinatorLayout, "There is no connection", BaseTransientBottomBar.LENGTH_SHORT);

                }
            }
        });

        if (savedInstanceState != null) {
            int currentNumberLabel = savedInstanceState.getInt("currentNumberLabel");
            String quotation = savedInstanceState.getString("quotation");
            String author = savedInstanceState.getString("author");
            addVisible = savedInstanceState.getBoolean("addVisible");

            tvDentroSv.setText(quotation);
            sampleText.setText(author);
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());

            String username = prefs.getString("username", "");
            String name = getString(R.string.name);

            if (username.equals("")) {
                username = name;
            }

            String hello = getString(R.string.hello, username);

            addVisible = false;

            tvDentroSv.setText(hello);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        //MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        thisMenu = menu;
        //thisMenu.findItem(R.id.floatingFavourite).setVisible(addVisible);

        if (addVisible) {
            view.findViewById(R.id.floatingFavourite).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.floatingFavourite).setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Añadir una nueva cita a favoritos
         if (item.getItemId() == R.id.obtain_new_quote) {

             //
             SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
             String request = prefs.getString("requests", "");

             hideAllActionBarOptionAndShowProgressBar();

             swipeRefreshLayout.setRefreshing(true);

             String req = prefs.getString("languages", "");

             if (isConnected()) {
                 if (request.equals("get")) {

                     Call<Quotation> call = retrofitInterface.getQuotation(req);

                     call.enqueue(new Callback<Quotation>() {
                         @Override
                         public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                             updateQuoteTextAndQuoteAuthor(response.body());
                         }

                         @Override
                         public void onFailure(Call<Quotation> call, Throwable t) {
                             updateQuoteTextAndQuoteAuthor(null);
                         }
                     });

                 } else {
                     Call<Quotation> call = retrofitInterface.postQuotation("getQuote", "json", req);

                     call.enqueue(new Callback<Quotation>() {
                         @Override
                         public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                             updateQuoteTextAndQuoteAuthor(response.body());
                         }

                         @Override
                         public void onFailure(Call<Quotation> call, Throwable t) {
                             updateQuoteTextAndQuoteAuthor(null);
                         }
                     });
                 }
             } else {
                 //Toast.makeText(requireContext(), "There is no connection", Toast.LENGTH_SHORT).show();
                 Snackbar.make(coordinatorLayout, "There is no connection", BaseTransientBottomBar.LENGTH_SHORT);
             }

             return true;
         } else {
             return super.onOptionsItemSelected(item);
         }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("quotation", tvDentroSv.getText().toString());
        outState.putString("author", sampleText.getText().toString());
        //outState.putInt("currentNumberLabel", quotesReceived);
        outState.putBoolean("addVisible", (view.findViewById(R.id.floatingFavourite).getVisibility() == View.VISIBLE));
    }

    public void showAddQuoteToFavouriteOption(Quotation quote) {
        // Si se encuentra en la base de datos se muestra la opción de añadir la cita a favoritos.
        if (quote != null) {
            addVisible = false;
        } else {
            addVisible = true;
        }

        // Se vuelve a llamar al método onCreateOptionsMenu()
        getActivity().invalidateOptionsMenu();
    }

    public String getTvDentroSv() {
        return tvDentroSv.getText().toString();
    }

    public boolean isConnected() {
        boolean result = false;
        ConnectivityManager manager = ( ConnectivityManager ) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION. SDK_INT > 22 ) {
            final Network activeNetwork = manager.getActiveNetwork();
            if (activeNetwork != null ) {
                final NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(activeNetwork);
                result = networkCapabilities != null && (
                        networkCapabilities.hasTransport(NetworkCapabilities. TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities. TRANSPORT_WIFI));
            }
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            result = ((info != null) && (info.isConnected()));
        }
        return result;
    }

    public void hideAllActionBarOptionAndShowProgressBar() {
        view.findViewById(R.id.floatingFavourite).setVisibility(View.INVISIBLE);
        //thisMenu.findItem(R.id.floatingFavourite).setVisible(false);
        thisMenu.findItem(R.id.obtain_new_quote).setVisible(false);
        swipeRefreshLayout.setRefreshing(true);
    }

    public void updateQuoteTextAndQuoteAuthor(Quotation quotation) {

        if (quotation == null) {
            //Toast.makeText(requireContext(), "No fue posible obtener una cita", Toast.LENGTH_SHORT).show();
            Snackbar.make(coordinatorLayout, "No fue posible obtener una cita", BaseTransientBottomBar.LENGTH_SHORT);
        } else {
            tvDentroSv.setText(quotation.getQuoteText());
            sampleText.setText(quotation.getQuoteAuthor());

            swipeRefreshLayout.setRefreshing(false);

            // Llamamos al hilo
            new QuotationThread(this).start();
        }
    }

}