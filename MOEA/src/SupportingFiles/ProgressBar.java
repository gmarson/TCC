package SupportingFiles;

/**
 * Created by gabrielm on 22/04/17.
 */
public class ProgressBar {

    private Double maxValue;
    private Double progressCounter = 0.0;
    private String initialChar = "\r|";
    private String finalChar = "|";
    private String progressCounterChar = "=";
    private String progress;
    private String overallProgress = " "+progressCounter+"%";
    private String currentProgressBar;

    public ProgressBar(Double maxValue){
        this.maxValue = maxValue;
        setCurrentProgressBar();
        System.out.print(currentProgressBar);
    }


    public void addJobDone(){
        removeSpace();
        addProgressCounterChar();
        updateOverallProgress();
        currentProgressBar = initialChar + progress + finalChar + overallProgress;
        System.out.print(currentProgressBar);
    }

    private void setCurrentProgressBar(){
        progress = "";
        for (int i = 0; i < this.maxValue; i++) {
            progress += " ";
        }
        currentProgressBar = initialChar + progress + finalChar + overallProgress ;
    }


    private void removeSpace() {
        if (progress != null && progress.length() > 0 && progress.charAt(progress.length()-1)==' ') {
            progress = progress.substring(0, progress.length()-1);
        }
    }

    private void addProgressCounterChar(){
        progress = progressCounterChar + progress;
    }

    private void updateOverallProgress(){
        progressCounter++;

        overallProgress = " "+(progressCounter/maxValue)*100.0+"%";
    }


}
