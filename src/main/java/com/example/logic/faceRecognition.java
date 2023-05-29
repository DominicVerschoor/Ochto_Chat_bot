//package com.example.logic;
//import org.opencv.core.*;
//import org.opencv.features2d.DescriptorMatcher;
//import org.opencv.features2d.Features2d;
//import static com.example.logic.faceDetector.faceDetector;
//import org.opencv.features2d.ORB;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
//
//import java.util.ArrayList;
//
//import static org.opencv.highgui.HighGui.destroyAllWindows;
//
//public class faceRecognition {
//    public static void main(String[] args) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        faceRecognition();
//    }
//
//    public static void faceRecognition() {
//
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        VideoCapture cam = new VideoCapture(0);
//        MatOfRect faces = null;
//        if (cam.isOpened()) {
//            faces = faceDetector(cam);
//        }
//        cam.release();
//        destroyAllWindows();
//        System.out.println(faces.toArray().length);
//        if (faces.toArray().length > 0) {
//            System.out.println("Face detected");
//            Imgcodecs imageCodecs = new Imgcodecs();
//            ArrayList<String> files = new ArrayList<String>();
//
//            Mat finalImg = null;
//
//            String person1 = "Python_facial_recognition/model_1/faces/Arjen.jpg";
//            String person2 = "Python_facial_recognition/model_1/faces/Dominic.jpg";
//            String person3 = "Python_facial_recognition/model_1/faces/Mohammed.jpg";
//            String person4 = "Python_facial_recognition/model_1/faces/Niklas.jpg";
//
//            files.add(person1);
//            files.add(person2);
//            files.add(person3);
//            files.add(person4);
//
//
//            // getting picture from camera
//            VideoCapture capture = new VideoCapture(0);
//            Mat frame = new Mat();
//            capture.read(frame);
//
//            // Convert the picture from the camera to grayscale
//            Mat grayFrame = new Mat();
//            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
//
//
//
//
//            double score = 0;
//            double new_score = 0;
//            for(String file : files) {
//                score = new_score;
//                Mat grayImage = new Mat();
//                Mat img = imageCodecs.imread(file);
//                Imgproc.cvtColor(img, grayImage, Imgproc.COLOR_BGR2GRAY);
//                Imgproc.resize(grayImage, grayImage, grayFrame.size());
//
//                ORB orb = ORB.create(1000);
//                MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
//                MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
//                Mat descriptors1 = new Mat();
//                Mat descriptors2 = new Mat();
//                orb.detectAndCompute(grayImage, new Mat(), keypoints1, descriptors1);
//                orb.detectAndCompute(grayFrame, new Mat(), keypoints2, descriptors2);
//
//
//                MatOfDMatch matches = new MatOfDMatch();
//                DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
//                matcher.match(descriptors1, descriptors2, matches);
//                MatOfDMatch goodMatches = new MatOfDMatch();
//                for (DMatch match : matches.toArray()) {
//                    if (match.distance < 60) {
//                        goodMatches.push_back(new MatOfDMatch(match));
//                    }
//                }
//
//
//                // Draw matches and calculate score
//                Mat outputImg = new Mat();
//                Scalar matchColor = new Scalar(0, 255, 0);
//                Features2d.drawMatches(grayImage, keypoints1, grayFrame, keypoints2, goodMatches, outputImg, matchColor, new Scalar(0, 0, 255), new MatOfByte(), 2);
//                double maxPossibleMatches = Math.min(keypoints1.size().height, keypoints2.size().height);
//                new_score = (double) goodMatches.size().height / maxPossibleMatches;
//
//                System.out.println("Matching score: " + new_score);
//                if(new_score > score){
//                    finalImg = outputImg;
//                    String finalName = file.substring(file.lastIndexOf("\\") + 1);
//                    System.out.println(finalName);
//                }
//                // Display output image
//                if(finalImg != null) {
//                    Imgcodecs.imwrite("finalImg.png", finalImg);
//                }
//            }
//
//
//        }
//    }
//}
