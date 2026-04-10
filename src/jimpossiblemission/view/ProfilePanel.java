package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import jimpossiblemission.model.User;

/**
 * Panel that shows the list of users
 * 
 * @author Filippo Taiuti
 *
 */
public class ProfilePanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JList<User> profileList;
    private DefaultListModel<User> listModel;

    private JButton createBtn;
    private JButton menuBtn;
    private JButton selectBtn;

    /**
     * Constructor to JPanel used to show users
     * Users are passed as list
     * 
     * @param users a list of users
     */
    public ProfilePanel(List<User> users)
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.BLUE);

        JLabel title = new JLabel("Profile Manager", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();

        JPanel JPUsers = new JPanel();
        JPUsers.setLayout(new BoxLayout(JPUsers, BoxLayout.Y_AXIS));
        JPUsers.setBackground(Color.GREEN);

        profileList = new JList<>(listModel);
        profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        profileList.setFont(new Font("Monospaced", Font.PLAIN, 16));
        profileList.setCellRenderer(new ProfileRenderer());
        profileList.setSelectionForeground(Color.BLUE);
        profileList.setSelectionBackground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(profileList);

        listModel.clear();
        for (User profile : users)
        {
            listModel.addElement(profile);
        }

        JPUsers.add(scrollPane);

        createBtn = new JButton("Create New Profile");
        JPUsers.add(createBtn);
        add(JPUsers, BorderLayout.CENTER);

        selectBtn = new JButton("Select Profile");
        menuBtn = new JButton("Go to Menu");
        menuBtn.setVisible(false);

        selectBtn.addActionListener(e ->
        {
            User selected = profileList.getSelectedValue();
            if (selected != null)
            {
                menuBtn.setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLUE);

        buttonPanel.add(selectBtn);
        buttonPanel.add(createBtn);
        buttonPanel.add(menuBtn);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Adds listener to ProfilelList
     * 
     * @param listener ListSelectionListener
     */
    public void addProfileListListener(ListSelectionListener listener)
    {
        profileList.addListSelectionListener(listener);
    }

    /**
     * Adds listener to create a new profile
     * 
     * @param listener ActionListener
     */
    public void addCreateNewProfileListener(ActionListener listener)
    {
        createBtn.addActionListener(listener);
    }

    /**
     * Adds listener to SelectProfile
     * 
     * @param listener ActionListener
     */
    public void addSelectProfileListener(ActionListener listener)
    {
        selectBtn.addActionListener(listener);
    }

    /**
     * Get the selected profile
     * 
     * @return the profile selected
     */
    public User getSelectedProfile()
    {
        return profileList.getSelectedValue();
    }

}
