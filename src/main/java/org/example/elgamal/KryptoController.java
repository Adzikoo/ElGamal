package org.example.elgamal;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KryptoController {

    @FXML
    private TextField informationBlock;

    @FXML
    private TextField keyTextField1, keyTextField2, keyTextField3, keyTextField4,loadKey,saveKey;

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
        // Obsługa przeciągania pliku do pola loadKey
        loadKey.setOnDragOver(event -> {
            if (event.getGestureSource() != loadKey && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        loadKey.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                loadKey.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        // Obsługa przeciągania pliku do pola saveKey
        saveKey.setOnDragOver(event -> {
            if (event.getGestureSource() != saveKey && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        saveKey.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                saveKey.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        // Obsługa przeciągania pliku do pola filePathEncryptJawny
        filePathEncryptJawny.setOnDragOver(event -> {
            if (event.getGestureSource() != filePathEncryptJawny && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        filePathEncryptJawny.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                filePathEncryptJawny.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        // Obsługa przeciągania pliku do pola filePathDecryptSzyfrogram
        filePathDecryptSzyfrogram.setOnDragOver(event -> {
            if (event.getGestureSource() != filePathDecryptSzyfrogram && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        filePathDecryptSzyfrogram.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                filePathDecryptSzyfrogram.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        // Obsługa przeciągania pliku do pola filePathSaveJawny
        filePathSaveJawny.setOnDragOver(event -> {
            if (event.getGestureSource() != filePathSaveJawny && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        filePathSaveJawny.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                filePathSaveJawny.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        // Obsługa przeciągania pliku do pola filePathSaveSzyfrogram
        filePathSaveSzyfrogram.setOnDragOver(event -> {
            if (event.getGestureSource() != filePathSaveSzyfrogram && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        filePathSaveSzyfrogram.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0); // pierwszy plik
                filePathSaveSzyfrogram.setText(file.getAbsolutePath());
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
        keyPair = ElGamal.generateKeys(512);
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
            onEncryptFileClick();
        }
    }

    @FXML
    protected void onDecryptClick() {
        if (radioText.isSelected()) {
            decryptText();
        } if (radioFile.isSelected()) {
            onDecryptFileClick();
        }
    }

    @FXML
    protected void encryptText() {
        informationBlock.setText("");
        encryptedTextField.setText("");

        String plainText = inputTextField.getText();

        try {
            BigInteger g = new BigInteger(keyTextField1.getText(), 16);
            BigInteger h = new BigInteger(keyTextField2.getText(), 16);
            BigInteger p = new BigInteger(keyTextField4.getText(), 16);

            ElGamal.PublicKey publicKey = new ElGamal.PublicKey(p, g, h);

            if (plainText.isEmpty()) {
                informationBlock.setText("Błąd: Brak tekstu!");
                return;
            }

            ArrayList<ElGamal.CipherText> encrypted = new ArrayList<>();

            for (char c : plainText.toCharArray()) {
                BigInteger m = BigInteger.valueOf((int) c);
                if (m.compareTo(p) >= 0) {
                    informationBlock.setText("Błąd: znak '" + c + "' większy niż p.");
                    return;
                }
                encrypted.add(ElGamal.encryptChar(m, publicKey));
            }

            // Zapisz każdy szyfrogram jako "(c1,c2)"
            String encryptedString = encrypted.stream()
                    .map(ElGamal.CipherText::toString)
                    .collect(Collectors.joining(" "));
            encryptedTextField.setText(encryptedString);
            informationBlock.setText("Sukces: Zaszyfrowano wiadomość.");
        } catch (Exception e) {
            informationBlock.setText("Błąd szyfrowania: " + e.getMessage());
        }
    }


    @FXML
    protected void decryptText() {
        informationBlock.setText("");

        String encryptedText = encryptedTextField.getText();
        String aText = keyTextField3.getText();
        String pText = keyTextField4.getText();

        if (encryptedText.isEmpty()) {
            informationBlock.setText("Błąd: Brak szyfrogramu!");
            return;
        }

        try {
            BigInteger a = new BigInteger(aText, 16);
            BigInteger p = new BigInteger(pText, 16);
            ElGamal.PrivateKey privKey = new ElGamal.PrivateKey(p, a);

            String[] cipherPairs = encryptedText.trim().split(" ");
            StringBuilder result = new StringBuilder();

            for (String pair : cipherPairs) {
                ElGamal.CipherText ct = ElGamal.CipherText.fromString(pair);
                BigInteger m = ElGamal.decryptChar(ct, privKey);
                result.append((char) m.intValue());
            }

            inputTextField.setText(result.toString());
            informationBlock.setText("Sukces: Odszyfrowano wiadomość.");
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



//    // Konwersja HEX -> bajty
//    private byte[] hexToBytes(String hex) {
//        int len = hex.length();
//        byte[] data = new byte[len / 2];
//        for (int i = 0; i < len; i += 2) {
//            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
//                    + Character.digit(hex.charAt(i + 1), 16));
//        }
//        return data;
//    }
//
//    // Walidacja poprawności klucza (16 znaków HEX)
//    private boolean isValidHexKey(String key) {
//        return key != null && key.matches("[0-9A-Fa-f]{16}");
//    }



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


    @FXML
    protected void onEncryptFileClick() {
        String filePath = filePathEncrypt.getText();
        informationBlock.setText("");

        if (filePath.isEmpty()) {
            informationBlock.setText("Błąd: Brak ścieżki do pliku!");
            return;
        }

        File selectedFile = new File(filePath);
        if (!selectedFile.exists()) {
            informationBlock.setText("Błąd: Plik nie istnieje!");
            return;
        }

        try {
            BigInteger g = new BigInteger(keyTextField1.getText(), 16);
            BigInteger h = new BigInteger(keyTextField2.getText(), 16);
            BigInteger p = new BigInteger(keyTextField4.getText(), 16);

            ElGamal.PublicKey publicKey = new ElGamal.PublicKey(p, g, h);

            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
            ArrayList<ElGamal.CipherText> encrypted = new ArrayList<>();

            for (byte b : fileBytes) {
                BigInteger m = BigInteger.valueOf(b & 0xFF); // bajt jako dodatni BigInteger
                if (m.compareTo(p) >= 0) {
                    informationBlock.setText("Błąd: Bajt większy lub równy p!");
                    return;
                }
                encrypted.add(ElGamal.encryptChar(m, publicKey));
            }

            // Zapisujemy zaszyfrowane bajty jako linie tekstu
            File outFile = new File(filePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                for (ElGamal.CipherText ct : encrypted) {
                    writer.write(ct.toString());
                    writer.newLine();
                }
            }

            informationBlock.setText("Sukces: Zaszyfrowano do '" + outFile.getName() + "'");
        } catch (NumberFormatException e) {
            informationBlock.setText("Błąd: Klucze muszą być w HEX.");
        } catch (IOException e) {
            informationBlock.setText("Błąd odczytu/zapisu pliku.");
        } catch (Exception e) {
            informationBlock.setText("Błąd szyfrowania: " + e.getMessage());
        }
    }


       @FXML
    protected void onDecryptFileClick() {
        String filePath = filePathDecrypt.getText();
        informationBlock.setText("");

        if (filePath.isEmpty()) {
            informationBlock.setText("Błąd: Brak ścieżki do pliku!");
            return;
        }

        File selectedFile = new File(filePath);
        if (!selectedFile.exists()) {
            informationBlock.setText("Błąd: Plik nie istnieje!");
            return;
        }

        try {

            BigInteger a = new BigInteger(keyTextField3.getText(), 16); // prywatny
            BigInteger p = new BigInteger(keyTextField4.getText(), 16);

            ElGamal.PrivateKey privateKey = new ElGamal.PrivateKey(p, a);

            List<String> lines = Files.readAllLines(selectedFile.toPath());
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            for (String line : lines) {
                if (line.isBlank()) continue;
                // Parsujemy linie w formacie (c1,c2)
                line = line.trim().replace("(", "").replace(")", "");
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    informationBlock.setText("Błąd: Nieprawidłowy format linii!");
                    return;
                }
                BigInteger c1 = new BigInteger(parts[0].trim());
                BigInteger c2 = new BigInteger(parts[1].trim());
                ElGamal.CipherText ct = new ElGamal.CipherText(c1, c2);
                BigInteger decrypted = ElGamal.decryptChar(ct, privateKey);

                output.write(decrypted.byteValue());
            }

            File outFile = new File( filePath );
            Files.write(outFile.toPath(), output.toByteArray());

            informationBlock.setText("Sukces: Odszyfrowano do '" + outFile.getName() + "'");
        } catch (NumberFormatException e) {
            informationBlock.setText("Błąd: Klucze muszą być w HEX.");
        } catch (IOException e) {
            informationBlock.setText("Błąd odczytu/zapisu pliku.");
        } catch (Exception e) {
            informationBlock.setText("Błąd deszyfrowania: " + e.getMessage());
        }
    }


//    private String bytesToHex(byte[] bytes) {
//        StringBuilder sb = new StringBuilder();
//        for (byte b : bytes) {
//            sb.append(String.format("%02x", b));
//        }
//        return sb.toString();
//    }



}
