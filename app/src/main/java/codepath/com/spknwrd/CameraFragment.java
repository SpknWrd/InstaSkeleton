package codepath.com.spknwrd;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CameraFragment extends Fragment {
    @BindView(R.id.camera)
    CameraView camera;
    @BindView(R.id.click)
    Button click;

    private Unbinder unbinder;

    public interface OnItemSelectedListener {
        public void onPicture(byte [] b );
    }
    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_camera, parent, false);
        unbinder = ButterKnife.bind(this, view);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.captureImage();
            }
        });
        camera.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                listener.onPicture(cameraKitImage.getJpeg());
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }
    @Override
    public void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    public void onPause() {
        camera.stop();
        super.onPause();
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
