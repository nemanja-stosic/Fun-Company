package controller;

import model.HibernateUtil;
import model.Staff;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import view.HibernateActionListener;
import view.UserInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Controller implements HibernateActionListener {
    public UserInterface userInterface;

    @Override
    public void fillTableData(DefaultTableModel tableModel) {
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String q = "from Staff";
            Query query = session.createQuery(q);

            List<Staff> staffList = query.list();
            for (Staff staff : staffList) {
                tableModel.insertRow(tableModel.getRowCount(), new Object[]{staff.getStaffId(), staff.getName(), staff.getAge(), staff.getAddress(), staff.getPaycheck()});
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.close();
        }
    }

    @Override
    public void addNewStaff(Staff staff) {
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.persist(staff);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.close();
        }
    }

    @Override
    public void updateStaff(Staff staff) {
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.update(staff);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.close();
        }
    }

    @Override
    public void deleteStaff(int id) {
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Staff retrievedStaff = session.load(model.Staff.class, id);
            session.delete(retrievedStaff);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.close();
        }
    }

    @Override
    public void searchStaffByName(String name, DefaultTableModel tableModel, JTable table) {
        boolean nameExist = false;

        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String q = "from Staff as staff where lower(staff.name) like :inputName";

            Query query = session.createQuery(q);

            query.setParameter("inputName", name);

            List<Staff> staffList = query.list();
            for (Staff staff : staffList) {
                if (name.equalsIgnoreCase(staff.getName())) {
                    tableModel = (DefaultTableModel) table.getModel();
                    tableModel.setRowCount(0);

                    tableModel.insertRow(tableModel.getRowCount(), new Object[]{staff.getStaffId(), staff.getName(), staff.getAge(), staff.getAddress(), staff.getPaycheck()});
                    nameExist = true;
                }
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.close();
        }

        if (!nameExist) {
            tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);

            JOptionPane.showMessageDialog(null, "Staff with name: " + name + " does not exist.");
            nameExist = false;
        }
    }

    @Override
    public Object getStaffById(int id) {
        Object tempObject = null;
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String q = "from Staff staff where staff.staffId=:staffIdInput";

            Query query = session.createQuery(q);
            query.setParameter("staffIdInput", id);

            tempObject = query.list().get(0);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            HibernateUtil.close();
        }
        return tempObject;
    }
}
