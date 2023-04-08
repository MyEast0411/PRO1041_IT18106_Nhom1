/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.Bia;
import domainModel.BiaKhuyenMai;
import domainModel.BiaKhuyenMaiId;
import domainModel.DotKhuyenMai;
import domainModel.KhachHangKhuyenMai;
import domainModel.NSXKhuyenMai;
import domainModel.SanPhamChiTiet;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utilities.HibernateUtil;

/**
 *
 * @author NGUYEN VAN HOI
 */
public class DotKhuyenMaiRepository {

    public List<DotKhuyenMai> getListt() {
        List<DotKhuyenMai> list = new ArrayList<>();
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("FROM DotKhuyenMai");
        list = q.getResultList();
        s.close();
        return list;
    }

    public List<DotKhuyenMai> getListByDate(Date startDate, Date endDate) {
        List<DotKhuyenMai> list = new ArrayList<>();
        try ( Session s = HibernateUtil.getSession()) {
            String hql = "FROM DotKhuyenMai";
            if (startDate != null && endDate != null) {
                hql += " WHERE ngayBatDau >= :startDate AND ngayKetThuc <= :endDate";
            }
            Query q = s.createQuery(hql);
            if (startDate != null && endDate != null) {
                q.setParameter("startDate", startDate);
                q.setParameter("endDate", endDate);
            }
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SanPhamChiTiet> getListSPCT() {
        List<SanPhamChiTiet> list = new ArrayList<>();
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("FROM SanPhamChiTiet");
        list = q.getResultList();
        s.close();
        return list;
    }

    public List<BiaKhuyenMai> getListBiaKhuyenMai() {
        List<BiaKhuyenMai> list = new ArrayList<>();
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("FROM BiaKhuyenMai bkm ORDER BY bkm.khuyenMai.ngayBatDau ASC"
        );
        list = q.getResultList();
        s.close();
        return list;
    }

    public List<KhachHangKhuyenMai> getListKhachHangKhuyenMai() {
        List<KhachHangKhuyenMai> list = new ArrayList<>();
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("FROM KhachHangKhuyenMai");
        list = q.getResultList();
        s.close();
        return list;
    }

    public List<NSXKhuyenMai> getListNSXKhuyenMai() {
        List<NSXKhuyenMai> list = new ArrayList<>();
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("FROM NSXKhuyenMai");
        list = q.getResultList();
        s.close();
        return list;
    }

    public Boolean insert(DotKhuyenMai x) {
        try ( Session s = HibernateUtil.getSession();) {
            s.getTransaction().begin();
            x.setId(UUID.randomUUID());
            List<DotKhuyenMai> existingPromotions = s.createQuery("FROM DotKhuyenMai").list();

            for (DotKhuyenMai p : existingPromotions) {
                if (x.getNgayBatDau().compareTo(p.getNgayKetThuc()) <= 0 && x.getNgayKetThuc().compareTo(p.getNgayBatDau()) >= 0) {
                    return false;
                }
            }
            s.save(x);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateDuplicateMaDKM(DotKhuyenMai x) {
        try ( Session s = HibernateUtil.getSession()) {
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaQuery<DotKhuyenMai> query = builder.createQuery(DotKhuyenMai.class);
            Root<DotKhuyenMai> root = query.from(DotKhuyenMai.class);
            query.select(root).where(builder.equal(root.get("ma"), x.getMa()));

            List<DotKhuyenMai> result = s.createQuery(query).getResultList();
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mã đợt khuyến mại đã tồn tại.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(DotKhuyenMai x) {
        try ( Session s = HibernateUtil.getSession()) {
            DotKhuyenMai dkm = s.get(DotKhuyenMai.class, x.getId());
            dkm.setMa(x.getMa());
            dkm.setTen(x.getTen());
            dkm.setGiaTriPhanTram(x.getGiaTriPhanTram());
            dkm.setGiaTriTienMat(x.getGiaTriTienMat());
            dkm.setNgayBatDau(x.getNgayBatDau());
            dkm.setNgayKetThuc(x.getNgayKetThuc());

            List<BiaKhuyenMai> biaKhuyenMaiList = s.createQuery("FROM BiaKhuyenMai WHERE khuyenMai = :khuyenMai", BiaKhuyenMai.class)
                    .setParameter("khuyenMai", dkm)
                    .getResultList();
            s.getTransaction().begin();
            for (BiaKhuyenMai biaKhuyenMai : biaKhuyenMaiList) {
                if (biaKhuyenMai.getTrangThai() == 1) { // kiểm tra giá trị thuộc tính trangThai
                    JOptionPane.showMessageDialog(null, "Đợt khuyến mại này đang diễn ra, không thể sửa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return false; // nếu có đối tượng đang diễn ra thì không cho phép cập nhật
                }
                biaKhuyenMai.setDonGia(biaKhuyenMai.getChiTietSanPham().getGiaBan());
                BigDecimal giaTriGiam = BigDecimal.ZERO;
                if (x.getGiaTriPhanTram() != null) {
                    giaTriGiam = biaKhuyenMai.getChiTietSanPham().getGiaBan().multiply(BigDecimal.valueOf(x.getGiaTriPhanTram())).divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
                } else if (x.getGiaTriTienMat() != null) {
                    giaTriGiam = x.getGiaTriTienMat();
                }
                biaKhuyenMai.setGiaConLai(biaKhuyenMai.getDonGia().subtract(giaTriGiam));
                s.update(biaKhuyenMai);
            }
            s.update(dkm);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMany(Date startDate, Date endDate) {
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "SELECT COUNT(*) FROM BiaKhuyenMai bkm WHERE bkm.khuyenMai.ngayBatDau >= :startDate AND bkm.khuyenMai.ngayKetThuc <= :endDate";
            Query query = session.createQuery(hql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            Long count = (Long) query.uniqueResult();
            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Đợt khuyến mại đã được áp dụng không thể xóa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            hql = "DELETE FROM DotKhuyenMai x WHERE x.ngayBatDau >= :startDate AND x.ngayKetThuc <= :endDate";
            query = session.createQuery(hql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            session.getTransaction().begin();
            int rows = query.executeUpdate();
            session.getTransaction().commit();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deleteOne(DotKhuyenMai x) {
        try ( Session s = HibernateUtil.getSession()) {
            // Kiểm tra xem bảng trung gian BiaKhuyenMai có sản phẩm nào áp dụng khuyến mại từ DotKhuyenMai cần xóa không
            String hql = "SELECT COUNT(*) FROM BiaKhuyenMai bm WHERE bm.khuyenMai = :khuyenMai";
            Query q = s.createQuery(hql);
            q.setParameter("khuyenMai", x);
            Long count = (Long) q.getSingleResult();
            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Đợt khuyến mại đã được áp dụng không thể xóa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Nếu không có sản phẩm áp dụng khuyến mại từ DotKhuyenMai này, thực hiện xóa
            hql = "SELECT x FROM DotKhuyenMai x WHERE x.id = :id";
            q = s.createQuery(hql);
            q.setParameter("id", x.getId());
            DotKhuyenMai dkm = (DotKhuyenMai) q.getSingleResult();
            s.getTransaction().begin();
            s.delete(dkm);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBiaKM(String tenSanPham, String maDotKhuyenMai) {
        try ( Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();

            // Tìm các bản ghi BiaKhuyenMai liên quan đến sản phẩm có tên được chọn và mã đợt khuyến mại được chọn
            Query<BiaKhuyenMai> query = session.createQuery(
                    "FROM BiaKhuyenMai bkm WHERE bkm.chiTietSanPham.bia.ten = :tenSanPham "
                    + "AND bkm.khuyenMai.ma = :maDotKhuyenMai", BiaKhuyenMai.class);
            query.setParameter("tenSanPham", tenSanPham);
            query.setParameter("maDotKhuyenMai", maDotKhuyenMai);
            List<BiaKhuyenMai> biaKhuyenMais = query.getResultList();

            // Xóa các bản ghi BiaKhuyenMai tìm được
            for (BiaKhuyenMai bkm : biaKhuyenMais) {
                session.delete(bkm);
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DotKhuyenMai> getListByTen(String tenDKM) {
        List<DotKhuyenMai> list = new ArrayList<>();
        try ( Session s = HibernateUtil.getSession()) {
            String hql = "SELECT x FROM DotKhuyenMai x where x.ten like :ten";
            Query q = s.createQuery(hql);
            q.setParameter("ten", "%" + tenDKM + "%");
            list = q.getResultList();
            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public DotKhuyenMai getDotKhuyenMaiByMaDKM(String maDKM) {
        try ( Session s = HibernateUtil.getSession()) {
            String hql = "SELECT x FROM DotKhuyenMai x where x.ma like :ma";
            Query q = s.createQuery(hql);
            q.setParameter("ma", "%" + maDKM + "%");
            List<DotKhuyenMai> list = q.getResultList();
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
            // xử lý ngoại lệ
        }
        return null;
    }

    public List<Bia> getListBia() {
        List<Bia> list = new ArrayList<>();
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("FROM Bia");
        list = q.getResultList();
        s.close();
        return list;
    }
    //-------------------------------------------------------------------------

    public boolean apDungKM(SanPhamChiTiet sp, DotKhuyenMai km) {
        try ( Session s = HibernateUtil.getSession()) {
            s.getTransaction().begin();

            // Check if the product already has a promotion applied for the selected promotion period
            Query query = s.createQuery("FROM BiaKhuyenMai WHERE chiTietSanPham = :sp AND khuyenMai = :km");
            query.setParameter("sp", sp);
            query.setParameter("km", km);
            List<BiaKhuyenMai> biaKMList = query.list();
            if (!biaKMList.isEmpty()) {
                return false;
            }
            // Apply the promotion to the product
            BiaKhuyenMai biaKhuyenMai = new BiaKhuyenMai();
            biaKhuyenMai.setKhuyenMai(km);
            biaKhuyenMai.setChiTietSanPham(sp);
            biaKhuyenMai.setDonGia(sp.getGiaBan());
            biaKhuyenMai.setGiaConLai(sp.getGiaBan().subtract(sp.getGiaBan().multiply(BigDecimal.valueOf(km.getGiaTriPhanTram() / 100))).subtract(km.getGiaTriTienMat()));
            Date currentDate = new Date();
            if (currentDate.after(km.getNgayBatDau()) && currentDate.before(km.getNgayKetThuc())) {
                biaKhuyenMai.setTrangThai(1); // Đang diễn ra
            } else if (currentDate.before(km.getNgayBatDau())) {
                biaKhuyenMai.setTrangThai(2); // Đã hết hạn
            } else if (currentDate.after(km.getNgayKetThuc())) {
                biaKhuyenMai.setTrangThai(0); // Chưa có hiệu lực
            }

            s.save(biaKhuyenMai);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGiaConLaiBiaKM(BiaKhuyenMai biaKhuyenMai, BigDecimal giaConLai) {
        try ( Session s = HibernateUtil.getSession()) {
            s.getTransaction().begin();
            // Update the discount price of the product promotion
            biaKhuyenMai.setGiaConLai(giaConLai);
            s.update(biaKhuyenMai);

            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTTBiaKM(SanPhamChiTiet sp, DotKhuyenMai km) {
        try ( Session s = HibernateUtil.getSession()) {
            s.getTransaction().begin();
            Query query = s.createQuery("update BiaKhuyenMai set trangThai = "
                    + "(case when :currentDate between (select km.ngayBatDau from DotKhuyenMai km where km.id = :idDotKhuyenMai) "
                    + "and (select km.ngayKetThuc from DotKhuyenMai km where km.id = :idDotKhuyenMai) then 1 "
                    + "when :currentDate < (select km.ngayBatDau from DotKhuyenMai km where km.id = :idDotKhuyenMai) then 2 else 0 end) "
                    + "where chiTietSanPham.id = :idSanPhamChiTiet and khuyenMai.id = :idDotKhuyenMai");

            query.setParameter("currentDate", new Date());
            query.setParameter("idSanPhamChiTiet", sp.getId());
            query.setParameter("idDotKhuyenMai", km.getId());
            int result = query.executeUpdate();
            s.getTransaction().commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkBiaKhuyenMai(SanPhamChiTiet spct, DotKhuyenMai dotKM) {
        try ( Session session = HibernateUtil.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BiaKhuyenMai> query = builder.createQuery(BiaKhuyenMai.class
            );
            Root<BiaKhuyenMai> root = query.from(BiaKhuyenMai.class
            );
            query.select(root)
                    .where(builder.equal(root.get("chiTietSanPham"), spct),
                            builder.equal(root.get("khuyenMai"), dotKM));
            List<BiaKhuyenMai> biaKMList = session.createQuery(query).getResultList();
            return !biaKMList.isEmpty();
        }
    }

    public List<SanPhamChiTiet> findByTenSanPham(String tenSanPham) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        try ( Session s = HibernateUtil.getSession()) {
            String hql = "SELECT x FROM SanPhamChiTiet x where x.bia.ten like :ten";
            Query q = s.createQuery(hql);
            q.setParameter("ten", "%" + tenSanPham + "%");
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            System.out.println("Error in findByTenSanPham: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
