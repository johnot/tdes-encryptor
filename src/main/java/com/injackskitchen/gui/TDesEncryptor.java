package com.injackskitchen.gui;

import com.injackskitchen.util.Crypto;
import com.injackskitchen.util.StringUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.injackskitchen.util.Crypto.DesMode;

public class TDesEncryptor extends JFrame {
    private final static String keyMask = "HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH HH";

    private static final String ECB_MODE = "ecbMode";
    private static final String CBC_MODE = "ccbMode";
    private static final String OFB_MODE = "ofmMode";
    private static final String CFB_MODE = "cfmMode";

    private JButton exitButton;
    private JButton encryptButton;
    private JFormattedTextField keyTextField;
    private JFormattedTextField encryptedValueField;
    private JFormattedTextField dataInputValueField;

    private JLabel enterKeyLabel;
    private JLabel dataInputLabel;
    private JLabel encryptedLabel;

    private JLabel modeLabel;

    private ButtonGroup modeGroup;

    public static void main(String[] args) {
        TDesEncryptor inst = new TDesEncryptor();
        inst.setVisible(true);
    }

    public TDesEncryptor() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
            createKeyInput();
            createModeInput();
            createDataInput();
            createEncryptButton();
            createEncryptedOutput();

            createExitButton();
            createFramAndTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createEncryptedOutput() {
        {
            encryptedLabel = new JLabel();
            getContentPane().add(encryptedLabel);
            encryptedLabel.setText("Encrypted Data:");
            encryptedLabel.setBounds(21, 464, 317, 34);
            encryptedLabel.setFont(new java.awt.Font("Arial", 1, 30));
        }
        {
            encryptedValueField = new JFormattedTextField();
            encryptedValueField.setValue(null);
            getContentPane().add(encryptedValueField);
            encryptedValueField.setBounds(21, 514, 1269, 34);
            encryptedValueField.setFont(new java.awt.Font("Arial", 1, 30));
            encryptedValueField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new java.awt.Color(0, 0, 0), 1, false),
                    null));
            encryptedValueField.setEditable(true);
        }
    }

    private void createEncryptButton() {
        encryptButton = new JButton();
        getContentPane().add(encryptButton);
        encryptButton.setText("Encrypt");
        encryptButton.setBounds(21, 375, 147, 42);
        encryptButton.setFont(new java.awt.Font("Arial", 1, 30));
        encryptButton.addActionListener(new ActionListener() {
            @SuppressWarnings("synthetic-access")
            public void actionPerformed(ActionEvent evt) {
                encryptButtonActionPerformed(evt);
            }
        });
    }

    private void createDataInput() {
        {
            dataInputLabel = new JLabel();
            getContentPane().add(dataInputLabel);
            dataInputLabel.setText("Enter data to encrypt:");
            dataInputLabel.setBounds(21, 244, 517, 30);
            dataInputLabel.setFont(new java.awt.Font("Arial", 1, 30));
        }
        {
            dataInputValueField = new JFormattedTextField();
            dataInputValueField.setValue(null);
            getContentPane().add(dataInputValueField);
            dataInputValueField.setBounds(21, 300, 1269, 34);
            dataInputValueField.setFont(new java.awt.Font("Arial", 1, 30));
            dataInputValueField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new java.awt.Color(0, 0, 0), 1, false),
                    null));
            dataInputValueField.setText("4811 1111 1111 1111");
            dataInputValueField.setEditable(true);
        }
    }

    private void createFramAndTitle() {
        {
            this.setTitle("Data Encryptor");
            getContentPane().setLayout(null);
        }
        this.setSize(1450, 827);

    }

    private void createExitButton() {
        exitButton = new JButton();
        getContentPane().add(exitButton);
        exitButton.setText("Exit");
        exitButton.setBounds(1094, 658, 147, 42);
        exitButton.setFont(new java.awt.Font("Arial", 1, 30));
        exitButton.setBackground(new java.awt.Color(236, 233, 216));
        exitButton.addActionListener(new ActionListener() {
            @SuppressWarnings("synthetic-access")
            public void actionPerformed(ActionEvent evt) {
                exitButonActionPerformed(evt);
            }
        });
    }

    private void createModeInput() {
        {
            modeLabel = new JLabel();
            getContentPane().add(modeLabel);
            modeLabel.setText("Select Mode:");
            modeLabel.setBounds(21, 165, 217, 34);
            modeLabel.setFont(new java.awt.Font("Arial", 1, 30));
        }
        {
            JPanel panel = new JPanel();
            panel.setBounds(21, 158, 794, 38);
            modeGroup = new ButtonGroup();
            JRadioButton ecb = new JRadioButton("ECB");
            ecb.setFont(new java.awt.Font("Arial", 1, 30));
            ecb.setActionCommand(ECB_MODE);
            ecb.setSelected(true);
            modeGroup.add(ecb);
            panel.add(ecb);

            JRadioButton cbc = new JRadioButton("CBC");
            cbc.setFont(new java.awt.Font("Arial", 1, 30));
            cbc.setActionCommand(CBC_MODE);
            modeGroup.add(cbc);
            panel.add(cbc);

            JRadioButton ofm = new JRadioButton("OFB");
            ofm.setFont(new java.awt.Font("Arial", 1, 30));
            ofm.setActionCommand(OFB_MODE);
            modeGroup.add(ofm);
            panel.add(ofm);

            JRadioButton cfm = new JRadioButton("CFB");
            cfm.setFont(new java.awt.Font("Arial", 1, 30));
            cfm.setActionCommand(CFB_MODE);
            modeGroup.add(cfm);
            panel.add(cfm);

            panel.setVisible(true);
            this.getContentPane().add(panel);
        }
    }

    private void createKeyInput() {
        {
            enterKeyLabel = new JLabel();
            getContentPane().add(enterKeyLabel);
            enterKeyLabel.setText("Enter Key:");
            enterKeyLabel.setBounds(21, 28, 217, 28);
            enterKeyLabel.setFont(new java.awt.Font("Arial", 1, 28));
        }
        {
            keyTextField = new JFormattedTextField(createFormatter(keyMask));
            keyTextField.setValue("10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10");
            getContentPane().add(keyTextField);
            keyTextField.setBounds(21, 63, 1269, 34);
            keyTextField.setFont(new java.awt.Font("Arial", 1, 30));
            keyTextField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new java.awt.Color(0, 0, 0), 1, false),
                    null));
            keyTextField.setEditable(true);
        }
    }

    private MaskFormatter createFormatter(String string) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(string);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    private void exitButonActionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
        System.exit(0);
    }

    private void encryptButtonActionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
        encryptedValueField.setText(null);

        byte[] key;
        byte[] dataToEncrypt;

        key = StringUtil.createBytesFromString(keyTextField.getText().replaceAll(" ", ""));
        dataToEncrypt = StringUtil.createConvertedBytesFromString(dataInputValueField.getText().replaceAll(" ", ""));

        DesMode desMode = getDesMode(modeGroup.getSelection().getActionCommand());

        byte[] encryptedData = Crypto.DESedeEncrypt(dataToEncrypt, key, "NoPadding", desMode);

        String stringFromBytes = StringUtil.createStringFromBytes(encryptedData);
        String outputToDisplay = StringUtil.createStringWithSpaces(stringFromBytes);

        encryptedValueField.setText(outputToDisplay);
    }

    private DesMode getDesMode(String actionCommand) {
        switch (actionCommand) {
            case ECB_MODE:
                return DesMode.DES_MODE_ECB;
            case OFB_MODE:
                return DesMode.DES_MODE_OFB;
            case CFB_MODE:
                return DesMode.DES_MODE_CFB;
            case CBC_MODE:
            default:
                return DesMode.DES_MODE_CBC;
        }
    }
}
