package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * The New User Panel is used to insert a new user.
 * 
 * @author Filippo Taiuti
 *
 */
public class NewUserPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private ButtonGroup avatarGroup;
    private JButton createBtn;
    private JTextField nickName;

    /**
     * Constructor to JPanel used to insert a new user
     *
     */
    public NewUserPanel()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.BLUE);
        JLabel title = new JLabel("New User", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel user = new JPanel();
        user.setLayout(new BoxLayout(user, BoxLayout.Y_AXIS));
        user.setBackground(Color.BLUE);

        user.add(Box.createVerticalGlue());

        JLabel name;
        name = new JLabel("Nickname: ");
        name.setFont(new Font("Arial", Font.PLAIN, 24));
        name.setForeground(Color.WHITE);

        nickName = new JTextField(16);
        nickName.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel nicknamePane = new JPanel(new GridBagLayout());
        nicknamePane.setBackground(Color.BLUE);

        nicknamePane.add(name);
        nicknamePane.add(nickName);

        user.add(nicknamePane);

        JPanel avatarPane = new JPanel(new GridBagLayout());
        avatarPane.setBackground(Color.WHITE);

        JLabel avatarLbl;
        avatarLbl = new JLabel("Scegli l'avatar: ");
        avatarLbl.setFont(new Font("Arial", Font.PLAIN, 24));
        avatarLbl.setForeground(Color.BLUE);
        avatarPane.add(avatarLbl);

        JRadioButton opt1, opt2, opt3;
        Border selected = BorderFactory.createLineBorder(Color.BLUE, 3);
        Border empty = BorderFactory.createEmptyBorder(3, 3, 3, 3);

        String spyPath1 = "/Image/Spy/spy1.png";
        opt1 = new JRadioButton(new ImageIcon(getClass().getResource(spyPath1)), true);
        avatarPane.add(opt1);
        opt1.setActionCommand(spyPath1);
        String spyPath2 = "/Image/Spy/spy2.png";
        opt2 = new JRadioButton(new ImageIcon(getClass().getResource(spyPath2)));
        opt2.setActionCommand(spyPath2);
        avatarPane.add(opt2);
        String spyPath3 = "/Image/Spy/spy3.png";
        opt3 = new JRadioButton(new ImageIcon(getClass().getResource(spyPath3)));
        opt3.setActionCommand(spyPath3);
        avatarPane.add(opt3);

        avatarGroup = new ButtonGroup();
        for (JRadioButton rb : new JRadioButton[] { opt1, opt2, opt3 })
        {
            rb.setBorderPainted(true);
            rb.setFocusPainted(false);
            rb.setContentAreaFilled(false);
            rb.setBorder(rb.isSelected() ? selected : empty);

            rb.addChangeListener(e -> rb.setBorder(rb.isSelected() ? selected : empty));
            avatarGroup.add(rb);
        }

        user.add(avatarPane);
        user.add(Box.createVerticalGlue());
        user.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        add(user, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLUE);

        createBtn = new JButton("Create New Profile");
        buttonPanel.add(createBtn);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Returns nickName
     * 
     * @return nickName as string
     */
    public String getNickName()
    {
        return nickName.getText();

    }

    /**
     * Returns the avatar image path
     * 
     * @return path of avatar image
     */
    public String getAvatarPath()
    {
        return avatarGroup.getSelection().getActionCommand();
    }

    /**
     * Adds listener to insert new user
     * 
     * @param listener actoinListener
     */
    public void addNewUserListener(ActionListener listener)
    {
        createBtn.addActionListener(listener);
    }

}
