package jimpossiblemission.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import jimpossiblemission.model.User;

/**
 * Cell to see some information of user profile
 * 
 * @author Filippo Taiuti
 *
 */
public class ProfileRenderer extends JLabel implements ListCellRenderer<User>
{
    private static final long serialVersionUID = 1L;
    private Border border;

    /**
     * Constructor of profileRederer
     * 
     */
    public ProfileRenderer()
    {
        setOpaque(true);
        border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 3),
                BorderFactory.createEmptyBorder(0, 40, 0, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends User> users, User user, int index, boolean isSelected,
            boolean cellHasFocus)
    {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(user.getAvatar()));
        if (isSelected)
        {
            setBackground(users.getSelectionBackground());
            setForeground(users.getSelectionForeground());
            setFont(users.getFont().deriveFont(Font.BOLD, 20f));
            setBorder(border);
        } else
        {
            setBackground(users.getBackground());
            setForeground(users.getForeground());
            setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            setFont(users.getFont());
        }
        setIcon(imageIcon);
        setText(user.toString());

        return this;

    }

}
