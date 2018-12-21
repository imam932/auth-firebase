package id.nawawi.fierbaseapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {
    private float x, y;
    private List<Integer> mImageIds;
    private Integer mListIndex, mListIndex0;
    private static String TAG = "ClothPartFragment";
    private ImageView iv;
    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";
    public BodyPartFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mengisi id saat instance state tersimpan kosong
        if (savedInstanceState != null) {
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex =  savedInstanceState.getInt(LIST_INDEX);
        }

        //inflate fragment layout
        View rootView = inflater.inflate(R.layout.body_part_fragment, container, false);

        //mengambil id image view pada layout
        iv = (ImageView) rootView.findViewById(R.id.body_part_view);

        //menampilkan gambar res ke imageview
        iv.setImageResource(mImageIds.get(mListIndex));



        return rootView;
    }

    //menyimpan variabel array & index
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        currentState.putInt(LIST_INDEX, mListIndex);
    }

    //setter list id gambar
    public void setmImageIds(List<Integer> mImageIds) {
        this.mImageIds = mImageIds;
    }

    //setter indeks
    public void setmListIndex(Integer mListIndex) {
        this.mListIndex = mListIndex;
    }

    //mengganti indeks dan gambar
    public void setmListIndexChange(Integer mListIndex) {
        this.mListIndex = mListIndex;
        iv.setImageResource(mImageIds.get(mListIndex));
    }
}
