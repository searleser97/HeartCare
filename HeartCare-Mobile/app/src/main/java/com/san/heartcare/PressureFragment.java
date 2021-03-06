package com.san.heartcare;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PressureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PressureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PressureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PressureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PressureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PressureFragment newInstance(String param1, String param2) {
        PressureFragment fragment = new PressureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public LineChart chart = null, chart2 = null;
    public List<Entry> entries = new ArrayList<>(), entries2 = new ArrayList<>();
    public LineDataSet dataSet = null, dataSet2 = null;
    public LineData data = null, data2 = null;
    public int aux = 1, aux2 = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pressure, container, false);
        chart = view.findViewById(R.id.live_chart);

        entries.add(new Entry(0, 100));

        dataSet = new LineDataSet(entries, "Presión");
        dataSet.setColors(Color.parseColor("#d60c0c"));
        dataSet.setLineWidth(2f);
        dataSet.setDrawValues(false);

        data = new LineData(dataSet);
        chart.setData(data);

        chart.setNoDataText("No hay Conexión con la pulsera");
        chart.setDrawBorders(false);
        chart.setDrawMarkers(false);
        chart.getDescription().setText("PPM");
        chart.getLegend().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        YAxis yAxisright = chart.getAxisRight();
        yAxisright.setEnabled(false);

        chart.invalidate();

        chart2 = view.findViewById(R.id.live_chart2);

        entries2.add(new Entry(0, 100));

        dataSet2 = new LineDataSet(entries2, "Presión");
        dataSet2.setColors(Color.parseColor("#0c0cd6"));
        dataSet2.setLineWidth(2f);
        dataSet2.setDrawValues(false);

        data2 = new LineData(dataSet2);
        chart2.setData(data2);

        chart2.setNoDataText("No hay Conexión con la pulsera");
        chart2.setDrawBorders(false);
        chart2.setDrawMarkers(false);
        chart2.getDescription().setText("PPM");
        chart2.getLegend().setEnabled(false);

        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setEnabled(false);

        YAxis yAxisright2 = chart2.getAxisRight();
        yAxisright2.setEnabled(false);

        chart2.invalidate();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
