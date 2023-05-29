//package com.example.logic;
//import org.opencv.core.*;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
//import org.opencv.objdetect.CascadeClassifier;
//
//import static org.opencv.highgui.HighGui.*;
//
//
//
//
//public class faceDetector {
//    public static void main(String[]args){
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        VideoCapture cam = new VideoCapture(0);
//
//        if (cam.isOpened()) {
//            MatOfRect faces = faceDetector(cam);
//        }
//        cam.release();
//        destroyAllWindows();
//    }
//
//    public static MatOfRect faceDetector(VideoCapture cam) {
//        CascadeClassifier faceDetector = new CascadeClassifier("src/main/java/com/example/logic/haarcascade_frontalface_defaultl.xml");
//        MatOfRect faces = new MatOfRect();
//
//        while (true) {
//            Mat frame = new Mat();
//            cam.read(frame);
//
//            Mat grayFrame = new Mat();
//            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
//
//
//            faceDetector.detectMultiScale(grayFrame, faces);
//
//            for (Rect rect : faces.toArray()) {
//                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
//            }
//            imshow("Webcam", frame);
//
//            if (faces.toArray().length > 0) {
//                break;
//            }
//            if (waitKey(1) == 'q') {
//                break;
//            }
//
//        }
//        return faces;
//    }
//
//
//
//}