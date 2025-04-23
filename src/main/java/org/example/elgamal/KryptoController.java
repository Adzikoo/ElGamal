package org.example.elgamal;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
public class KryptoController {

    @FXML
    private TextField informationBlock;

    @FXML
    private TextField keyTextField1, keyTextField2, keyTextField3, keyTextField4;


    @FXML
    private TextArea inputTextField;

    @FXML
    private TextArea encryptedTextField;

    @FXML
    private RadioButton radioText;

    @FXML
    private RadioButton radioFile;

    @FXML
    private TextField filePathEncrypt, filePathDecrypt,filePathEncryptJawny,filePathDecryptSzyfrogram,filePathSaveJawny,filePathSaveSzyfrogram;

    private ElGamal.KeyPair keyPair;

    private FileChooser fileChooser = new FileChooser();


    @FXML
    public void initialize() {
        inputTextField.setWrapText(true);
        encryptedTextField.setWrapText(true);

        // Obsługa przeciągania pliku do pola filePathEncrypt
        filePathEncrypt.setOnDragOver(event -> {
            if (event.getGestureSource() != filePathEncrypt && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        filePathEncrypt.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                filePathEncrypt.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Obsługa przeciągania pliku do pola filePathDecrypt
        filePathDecrypt.setOnDragOver(event -> {
            if (event.getGestureSource() != filePathDecrypt && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        filePathDecrypt.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                filePathDecrypt.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    @FXML
    private void onLoadPlaintextFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik z tekstem jawnym");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePathEncryptJawny.setText(file.getAbsolutePath());
            try {
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                inputTextField.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
                informationBlock.setText("Błąd: Nie udało się wczytać pliku z tekstem jawnym.");
            }
        }
    }

    @FXML
    private void onLoadCiphertextFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik z szyfrogramem");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePathDecryptSzyfrogram.setText(file.getAbsolutePath());
            try {
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                encryptedTextField.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
                informationBlock.setText("Błąd: Nie udało się wczytać pliku z szyfrogramem.");
            }
        }
    }
    @FXML
    private void onSavePlaintextFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz tekst jawny do pliku");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            filePathSaveJawny.setText(file.getAbsolutePath());
            try {
                Files.writeString(file.toPath(), inputTextField.getText(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                informationBlock.setText("Błąd: Nie udało się zapisać tekstu jawnego.");
            }
        }
    }

    @FXML
    private void onSaveCiphertextFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz szyfrogram do pliku");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            filePathSaveSzyfrogram.setText(file.getAbsolutePath());
            try {
                Files.writeString(file.toPath(), encryptedTextField.getText(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                informationBlock.setText("Błąd: Nie udało się zapisać szyfrogramu.");
            }
        }
    }


    @FXML
    protected void onGenerateKeyClick() {
        keyPair = ElGamal.generateKeys(2048); // np. 64-bit dla testów
        informationBlock.setText("Wygenerowano klucze ElGamala");
        keyTextField1.setText(keyPair.publicKey.g.toString(16));
        keyTextField2.setText(keyPair.publicKey.h.toString(16));
        keyTextField3.setText(keyPair.privateKey.a.toString(16));
        keyTextField4.setText(keyPair.publicKey.p.toString(16));
    }


    @FXML
    protected void onEncryptClick() {
        if (radioText.isSelected()) {
            encryptText();
        } if (radioFile.isSelected()){
            //onEncryptFileClick();
        }
    }

    @FXML
    protected void onDecryptClick() {
        if (radioText.isSelected()) {
            decryptText();
        } if (radioFile.isSelected()) {
            //onDecryptFileClick();
        }
    }

//    @FXML
//    protected void encryptText() {
//        informationBlock.setText("");
//        encryptedTextField.setText("");
//        String plainText = inputTextField.getText();
//        String keyHex1 = keyTextField.getText();
//        String keyHex2 = keyTextField2.getText();
//        String keyHex3 = keyTextField3.getText();
//
//        if (!isValidHexKey(keyHex1) || !isValidHexKey(keyHex2) || !isValidHexKey(keyHex3)) {
//            //przed wpisaniem czyść pole informacyjne
//
//            informationBlock.setText("Błąd: Brak kluczy!");
//            return;
//        }
//        if(plainText.isEmpty()) {
//            informationBlock.setText("Błąd: Brak tekstu do szyfrowania!");
//            return;
//        }
//        //wyswtlanie kluczy jako listy bajtów
//        byte[] key1 = hexToBytes(keyHex1);
//        byte[] key2 = hexToBytes(keyHex2);
//        byte[] key3 = hexToBytes(keyHex3);
//        System.out.println("Key 1: " + Arrays.toString(key1));
//        System.out.println("Key 2: " + Arrays.toString(key2));
//        System.out.println("Key 3: " + Arrays.toString(key3));
//
//        // Tworzenie klucza TripleDES
//        TripleDES.Key key = new TripleDES.Key(hexToBytes(keyHex1), hexToBytes(keyHex2), hexToBytes(keyHex3));
//
//        // Szyfrowanie do Base64
//        String encryptedBase64 = TripleDES.encryptToBase64(plainText.getBytes(StandardCharsets.UTF_8), key);
//
//        encryptedTextField.setText(encryptedBase64);
//            //Szyfrownie do bajtów na hex
////        byte[] encryptedBytes = TripleDES.encrypt3D(plainText.getBytes(StandardCharsets.UTF_8), key);
////        String encryptedHex = bytesToHex(encryptedBytes);
////        encryptedTextField.setText(encryptedHex);
//        informationBlock.setText("Sukces: Szyfrowanie zakończone.");
//    }

@FXML
protected void encryptText() {
    informationBlock.setText("");
    encryptedTextField.setText("");
    String plainText = inputTextField.getText();

    try {
        BigInteger g = new BigInteger(keyTextField1.getText(), 16);
        BigInteger h = new BigInteger(keyTextField2.getText(), 16);
        BigInteger a = new BigInteger(keyTextField3.getText(), 16); // tylko do deszyfrowania, tu niepotrzebne
        BigInteger p = new BigInteger(keyTextField4.getText(), 16);

        ElGamal.PublicKey publicKey = new ElGamal.PublicKey(p, g, h);

        if (plainText.isEmpty()) {
            informationBlock.setText("Błąd: Brak tekstu!");
            return;
        }

        BigInteger m = new BigInteger(plainText.getBytes(StandardCharsets.UTF_8));

        if (m.compareTo(p) >= 0) {
            informationBlock.setText("Błąd: wiadomość zbyt duża dla p.");
            return;
        }

        ElGamal.CipherText cipher = ElGamal.encrypt(m, publicKey);
        encryptedTextField.setText(cipher.toString());
        informationBlock.setText("Sukces: Zaszyfrowano wiadomość.");
    } catch (NumberFormatException e) {
        informationBlock.setText("Błąd: Klucze muszą być liczbami (HEX).");
    } catch (Exception e) {
        informationBlock.setText("Błąd szyfrowania: " + e.getMessage());
    }
}




//    @FXML
//    protected void decryptText() {
//        informationBlock.setText("");
//        inputTextField.setText("");
//        String encryptedText = encryptedTextField.getText();
//        String encryptedHex = encryptedTextField.getText();
//        String keyHex1 = keyTextField.getText();
//        String keyHex2 = keyTextField2.getText();
//        String keyHex3 = keyTextField3.getText();
//
//        if (!isValidHexKey(keyHex1) || !isValidHexKey(keyHex2) || !isValidHexKey(keyHex3)) {
//            informationBlock.setText("Błąd: Brak kluczy!");
//            return;
//        }
//        if(encryptedText.isEmpty()) {
//            informationBlock.setText("Błąd: Brak szyfrogramu!");
//            return;
//        }
//        byte[] key1 = hexToBytes(keyHex1);
//        byte[] key2 = hexToBytes(keyHex2);
//        byte[] key3 = hexToBytes(keyHex3);
//        System.out.println("Key 1: " + Arrays.toString(key1));
//        System.out.println("Key 2: " + Arrays.toString(key2));
//        System.out.println("Key 3: " + Arrays.toString(key3));
//
//        // Tworzenie klucza TripleDES
//        TripleDES.Key key = new TripleDES.Key(hexToBytes(keyHex1), hexToBytes(keyHex2), hexToBytes(keyHex3));
//
//        // Deszyfrowanie Base64
//        byte[] decryptedData = TripleDES.decryptFromBase64(encryptedText, key);
//        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
//
//        inputTextField.setText(decryptedText);
////          //Deszyfroawnie do bajtów na hex
////        byte[] encryptedBytes = hexToBytes(encryptedHex);
////        byte[] decryptedBytes = TripleDES.decrypt3D(encryptedBytes, key);
////        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
////        inputTextField.setText(decryptedText);
//        informationBlock.setText("Sukces: Deszyfrowanie zakończone.");
//    }

@FXML
protected void decryptText() {
    informationBlock.setText("");


    String encryptedText = encryptedTextField.getText();
    String aText = keyTextField3.getText(); // prywatny klucz
    String pText = keyTextField4.getText(); // p

    if (encryptedText.isEmpty()) {
        informationBlock.setText("Błąd: Brak szyfrogramu do odszyfrowania!");
        return;
    }

    if (aText.isEmpty() || pText.isEmpty()) {
        informationBlock.setText("Błąd: Brakuje klucza prywatnego (a) lub p!");
        return;
    }

    try {
        BigInteger a = new BigInteger((aText),16);
        BigInteger p = new BigInteger((pText),16);

        // Parsowanie szyfrogramu z formatu np. "(c1,c2)" lub "c1 c2"
        String[] parts = encryptedText.replaceAll("[^0-9, ]", "").split("[, ]+");
        if (parts.length != 2) {
            informationBlock.setText("Błąd: Nieprawidłowy format szyfrogramu!");
            return;
        }

        BigInteger c1 = new BigInteger(parts[0]);
        BigInteger c2 = new BigInteger(parts[1]);

        ElGamal.PrivateKey privateKey = new ElGamal.PrivateKey(p, a);
        ElGamal.CipherText cipher = new ElGamal.CipherText(c1, c2);

        BigInteger decrypted = ElGamal.decrypt(cipher, privateKey);
        String message = new String(decrypted.toByteArray(), StandardCharsets.UTF_8);

        inputTextField.setText(message);
        informationBlock.setText("Sukces: Odszyfrowano wiadomość.");

    } catch (NumberFormatException e) {
        informationBlock.setText("Błąd: Klucze i szyfrogram muszą być liczbami!");
    } catch (Exception e) {
        informationBlock.setText("Błąd deszyfrowania: " + e.getMessage());
    }
}



    @FXML
    protected void onLoadFileClick() {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePathEncrypt.setText(file.getAbsolutePath());
        }
    }

    @FXML
    protected void onLoadFileDecryptClick() {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePathDecrypt.setText(file.getAbsolutePath());
        }
    }



    // Konwersja HEX -> bajty
    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // Walidacja poprawności klucza (16 znaków HEX)
    private boolean isValidHexKey(String key) {
        return key != null && key.matches("[0-9A-Fa-f]{16}");
    }



    @FXML
    protected void onSaveKeysClick() {
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (FileWriter writer = new FileWriter(selectedFile)) {
                writer.write(keyTextField1.getText() + "\n");
                writer.write(keyTextField2.getText() + "\n");
                writer.write(keyTextField3.getText() + "\n");
                writer.write(keyTextField4.getText() + "\n");
                informationBlock.setText("Klucze zostały zapisane.");
            } catch (IOException e) {
                informationBlock.setText("Błąd zapisu kluczy.");
            }
        }
    }
    @FXML
    protected void onLoadKeysClick() {
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                String[] keys = content.split("\n");
                if (keys.length == 4) {
                    keyTextField1.setText(keys[0].trim());
                    keyTextField2.setText(keys[1].trim());
                    keyTextField3.setText(keys[2].trim());
                    keyTextField4.setText(keys[3].trim());
                    informationBlock.setText("Klucze zostały załadowane.");
                } else {
                    informationBlock.setText("Błąd: Niepoprawny format pliku z kluczami.");
                }
            } catch (IOException e) {
                informationBlock.setText("Błąd odczytu pliku.");
            }
        }
    }


//    @FXML
//    protected void onEncryptFileClick() {
//        String filePath = filePathEncrypt.getText();
//        if (filePath.isEmpty()) {
//            informationBlock.setText("Błąd: Brak ścieżki do pliku!");
//            return;
//        }
//
//        File selectedFile = new File(filePath);
//        if (!selectedFile.exists()) {
//            informationBlock.setText("Błąd: Plik nie istnieje!");
//            return;
//        }
//
//        try {
//            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
//
//            String keyHex1 = keyTextField.getText();
//            String keyHex2 = keyTextField2.getText();
//            String keyHex3 = keyTextField3.getText();
//
//            if (!isValidHexKey(keyHex1) || !isValidHexKey(keyHex2) || !isValidHexKey(keyHex3)) {
//                informationBlock.setText("Błąd: Brak kluczy!");
//                return;
//            }
//
//            byte[] keyBytes1 = hexToBytes(keyHex1);
//            byte[] keyBytes2 = hexToBytes(keyHex2);
//            byte[] keyBytes3 = hexToBytes(keyHex3);
//
//            TripleDES.Key key = new TripleDES.Key(keyBytes1, keyBytes2, keyBytes3);
//
//            byte[] encryptedData = TripleDES.encrypt3D(fileBytes, key);
//
//            Files.write(selectedFile.toPath(), encryptedData);
//
//            informationBlock.setText("Plik został zaszyfrowany.");
//        } catch (IOException e) {
//            informationBlock.setText("Błąd odczytu/zapisu pliku.");
//        }
//        informationBlock.setText("Sukces: Szyfrowanie zakończone.");
//    }
//
//    @FXML
//    protected void onDecryptFileClick() {
//        String filePath = filePathDecrypt.getText();
//        if (filePath.isEmpty()) {
//            informationBlock.setText("Błąd: Brak ścieżki do pliku!");
//            return;
//        }
//
//        File selectedFile = new File(filePath);
//        if (!selectedFile.exists()) {
//            informationBlock.setText("Błąd: Plik nie istnieje!");
//            return;
//        }
//
//        try {
//            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
//
//            String keyHex1 = keyTextField.getText();
//            String keyHex2 = keyTextField2.getText();
//            String keyHex3 = keyTextField3.getText();
//
//            if (!isValidHexKey(keyHex1) || !isValidHexKey(keyHex2) || !isValidHexKey(keyHex3)) {
//                informationBlock.setText("Błąd: Brak kluczy!");
//                return;
//            }
//
//            byte[] keyBytes1 = hexToBytes(keyHex1);
//            byte[] keyBytes2 = hexToBytes(keyHex2);
//            byte[] keyBytes3 = hexToBytes(keyHex3);
//
//            TripleDES.Key key = new TripleDES.Key(keyBytes1, keyBytes2, keyBytes3);
//
//            byte[] decryptedData = TripleDES.decrypt3D(fileBytes, key);
//
//            Files.write(selectedFile.toPath(), decryptedData);
//
//            informationBlock.setText("Plik został odszyfrowany.");
//        } catch (IOException e) {
//            informationBlock.setText("Błąd odczytu/zapisu pliku.");
//        }
//        informationBlock.setText("Sukces: Deszyfrowanie zakończone.");
//    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }



}
