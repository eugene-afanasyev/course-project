package main;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AboutWorldskillsController {
    @FXML
    private TextArea informationArea;

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView championshipImage;

    @FXML
    private ImageView presidentImage;

    @FXML
    private ImageView AbidalImage;

    private String AbidalText = "\"Fill youth with enthusiasm through special action! Convince young people's parents, trainers and company chiefs that a promising future is possible only through good vocational training\".\n" +
            "- Francisco Albert-Vidal, IVTO President 1984-1992\n" +
            "\n" +
            "It was his nephew, Juan Martinez Albert, and his friend, Jose Rubio, who asked for this nomination two years ago. \n" +
            "\n" +
            "Francisco Albert-Vidal was born in Pinoso in 1917 and worked in the city hall of the village of Petrer. Later, he was the Vocational Training General Secretary until 1977 as well as the founder of the International VET Competitions, now WorldSkills.\n" +
            "\n" +
            "He died in Madrid, in 1993, and was buried in the village of Pinoso.\n" +
            "\n" +
            "On 31 March 2015, the plenary session of the city hall of Pinoso approved, unanimously, his appointment as honorary citizen of the village, as recognition of his work in the field of vocational training for young people through the creation of important events like WorldSkills Competitions.\n" +
            "\n" +
            "Words from Pepe Rubio (partner and friend): \"Thanks for this recognition. Francisco Albert Vidal worked for universal values, for people and their education\" \n" +
            "\n" +
            "Words from Juan Martinez (nephew): \"I'm very excited, and I remember many family moments with him, especially together with my mother, the only sister of Francisco who is still alive. I want to transmit gratitude from my whole family.”";

    private String presidentText = "Simon Bartley\n" +
            "President\n" +
            "\n" +
            "(United Kingdom)\n" +
            "\n" +
            "Simon was elected President of WorldSkills International at the organization’s General Assembly in October 2010 and took up his four-year post on 10th October 2011, the day after the Closing Ceremony at WorldSkills London 2011. Previously Simon had been Chief Executive of UK Skills and WorldSkills London 2011 as well as being the UK’s Official Delegate to both WorldSkills International and WorldSkills Europe (on both of which he was also a Board Member). Prior to his involvement in UK Skills Simon worked in the Building Services Sector as Chairman of his family business and he now works as an international skills consultant. Simon was educated at Durham University, from where he graduated with a BSc in Engineering Science and Management and an MSc in Management Science. He is a Chartered Engineer, a Member of the Institution of Civil Engineers and a Fellow of City and Guilds. A Member of the UK Governments Skills Commission he is also on the Council of City & Guilds. Simon is a past Chair of the Confederation of British Industry’s Small and Medium Sized Enterprise Council.";

    private String championshipText = "Весной 2013 года в Тольятти состоялся Первый всероссийский конкурс профессионального мастерства «Национальный чемпионат WorldSkills Russia — 2013». В чемпионате приняли участие более трёхсот участников в возрасте от 18 до 22 лет — студентов учреждений среднего профессионального образования, победителей региональных конкурсов.\n" +
            "По результатам Национального чемпионата была сформирована сборная Российской Федерации, которая в июле 2013 года приняла участие в чемпионате мира WorldSkills International в Лейпциге (Германия).";
    @FXML
    void initialize() {
        try {
            logoImage.setImage(new Image(new FileInputStream("res/wsr_1nc.png")));
            AbidalImage.setImage(new Image(new FileInputStream("res/Albert_Vidal.jpg")));
            championshipImage.setImage(new Image(new FileInputStream("res/wsr_1nc.png")));
            presidentImage.setImage(new Image(new FileInputStream("res/Simon_Bartley.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        informationArea.setEditable(false);
        informationArea.setWrapText(true);
        informationArea.setEffect(null);

        AbidalImage.setOnMouseClicked(mouseEvent -> {
            informationArea.setText(AbidalText);
        });

        presidentImage.setOnMouseClicked(mouseEvent -> {
            informationArea.setText(presidentText);
        });

        championshipImage.setOnMouseClicked(mouseEvent -> {
            informationArea.setText(championshipText);
        });
    }

}