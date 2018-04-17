// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProcessHisPrice.java

package com.ylxx.fx.utils.price;

import java.io.*;
import java.util.*;
import java.util.zip.*;

// Referenced classes of package com.hr.qutserver.productbusiness:
//			ImportHisPrice
//	public class BrnchHisPriceCommand extends CommandImp
//	{
//
//		private static final long serialVersionUID = 0x2cd606e87a0e2712L;
//		final ProcessHisPrice this$0;
//
//		public void process()
//			throws CommandException
//		{
//			setReturnFlag(-1000);
//			if (!dealbrnchhisprice(ptid, brnchHislist))
//				logger.error("转移客户价格失败！");
//			setReturnFlag(0);
//		}
//
//		public BrnchHisPriceCommand()
//		{
//			this$0 = ProcessHisPrice.this;
//			super();
//		}
//	}
//
//	public class CustHisPriceCommand extends CommandImp
//	{
//
//		private static final long serialVersionUID = 0x5a67ead428c61850L;
//		final ProcessHisPrice this$0;
//
//		public void process()
//			throws CommandException
//		{
//			setReturnFlag(-1000);
//			if (!dealcusthisprice(ptid, custHislist))
//				logger.error("ת�ƿͻ��۸�ʧ�ܣ�");
//			setReturnFlag(0);
//		}
//
//		public CustHisPriceCommand()
//		{
//			this$0 = ProcessHisPrice.this;
//			super();
//		}
//	}
//
//	public class JSHHisPriceCommand extends CommandImp
//	{
//
//		private static final long serialVersionUID = 0xdaa71485181bda28L;
//		final ProcessHisPrice this$0;
//
//		public void process()
//			throws CommandException
//		{
//			setReturnFlag(-1000);
//			if (!dealjshhisprice(jshHislist))
//				logger.error("ת�ƽ��ۻ�۸�ʧ�ܣ�");
//			setReturnFlag(0);
//		}
//
//		public JSHHisPriceCommand()
//		{
//			this$0 = ProcessHisPrice.this;
//			super();
//		}
//	}
//
//
//	static SysLogger logger = SysLogger.getInstance(com/hr/qutserver/productbusiness/ProcessHisPrice.getName());
//	private IQut_pdtPreHisPrice pdtprehisprice;
//	private String ptid;
//	private List brnchHislist;
//	private List custHislist;
//	private List jshHislist;
//	private List pricelist;
//
//	public ProcessHisPrice()
//	{
//		pdtprehisprice = (IQut_pdtPreHisPrice)DaoFactory.getDaoObject("IQut_pdtPreHisPrice");
//		ptid = null;
//		brnchHislist = null;
//		custHislist = null;
//		jshHislist = null;
//		pricelist = new ArrayList();
//	}
//
//	public List getPricelist()
//	{
//		return pricelist;
//	}
//
//	public byte[] getBytePrice(List price)
//	{
//		byte p[] = (byte[])null;
//		try
//		{
//			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//			ObjectOutputStream out = new ObjectOutputStream(byteOut);
//			out.writeObject(price);
//			p = byteOut.toByteArray();
//		}
//		catch (IOException e)
//		{
//			logger.error((new StringBuilder("���л�ʧ�ܣ�")).append(e.getMessage()).toString());
//		}
//		byte b[] = (byte[])null;
//		try
//		{
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			ZipOutputStream zip = new ZipOutputStream(bos);
//			ZipEntry entry = new ZipEntry("zip");
//			entry.setSize(p.length);
//			zip.putNextEntry(entry);
//			zip.write(p);
//			zip.closeEntry();
//			zip.close();
//			b = bos.toByteArray();
//			bos.close();
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//			logger.error((new StringBuilder("ѹ��ʧ�ܣ�")).append(ex.getMessage()).toString());
//		}
//		return b;
//	}
//
//	public List getPriceFormByte(byte b[])
//	{
//		byte a[] = (byte[])null;
//		try
//		{
//			ByteArrayInputStream bis = new ByteArrayInputStream(b);
//			ZipInputStream zip;
//			ByteArrayOutputStream baos;
//			for (zip = new ZipInputStream(bis); zip.getNextEntry() != null; baos.close())
//			{
//				byte buf[] = new byte[1024];
//				int num = -1;
//				baos = new ByteArrayOutputStream();
//				while ((num = zip.read(buf, 0, buf.length)) != -1) 
//					baos.write(buf, 0, num);
//				a = baos.toByteArray();
//				baos.flush();
//			}
//
//			zip.close();
//			bis.close();
//		}
//		catch (Exception ex)
//		{
//			logger.error((new StringBuilder("��ѹ����ʧ�ܣ�")).append(ex.getMessage()).toString());
//		}
//		List pricelist = null;
//		try
//		{
//			ByteArrayInputStream byteIn = new ByteArrayInputStream(a);
//			ObjectInputStream in = new ObjectInputStream(byteIn);
//			List readObject2 = (List)in.readObject();
//			List readObject = readObject2;
//			pricelist = readObject;
//		}
//		catch (IOException e)
//		{
//			logger.error((new StringBuilder("�����л�ʧ�ܣ�")).append(e.getMessage()).toString());
//		}
//		catch (ClassNotFoundException e)
//		{
//			logger.error((new StringBuilder("�����л�ʧ�ܣ�")).append(e.getMessage()).toString());
//		}
//		return pricelist;
//	}
//
//	public boolean buildQutPdtPreHis(String ptid, Qut_pdtPreHis pdthisprice, boolean isBranch)
//	{
//		List price = null;
//		if (isBranch)
//			price = pdtprehisprice.getBrnchPrice(ptid, pdthisprice);
//		else
//			price = pdtprehisprice.getCustPrice(ptid, pdthisprice);
//		pdthisprice.setArraySize(price.size());
//		if (price.size() <= 0)
//			break MISSING_BLOCK_LABEL_73;
//		byte pricebyte[] = getBytePrice(price);
//		pdthisprice.setHisprice(pricebyte);
//		return true;
//		try
//		{
//			logger.error("û�в鵽�۸���Ϣ��");
//		}
//		catch (Exception e)
//		{
//			logger.error((new StringBuilder("��ѯ�۸���Ϣʧ�ܣ�")).append(e.getMessage()).toString());
//			return false;
//		}
//		return true;
//	}
//
//	public boolean buildJSHQutPdtPreHis(Qut_pdtPreHis pdthisprice)
//	{
//		String dates;
//		String exnm;
//		List price = null;
//		dates = pdthisprice.getBeginTime().substring(0, 8);
//		exnm = pdthisprice.getCurrencyPair();
//		List price = pdtprehisprice.getJSHPrice(exnm, dates);
//		pdthisprice.setArraySize(price.size());
//		if (price.size() <= 0)
//			break MISSING_BLOCK_LABEL_75;
//		System.out.println("price");
//		byte pricebyte[] = getBytePrice(price);
//		pdthisprice.setHisprice(pricebyte);
//		return true;
//		try
//		{
//			logger.error("û�в鵽�۸���Ϣ��");
//		}
//		catch (Exception e)
//		{
//			logger.error((new StringBuilder("��ѯ�۸���Ϣʧ�ܣ�")).append(e.getMessage()).toString());
//			return false;
//		}
//		return true;
//	}
//
//	public boolean dealcusthisprice(String ptid, List pdthisprice)
//	{
//		if (pdthisprice.size() > 0)
//		{
//			if (!pdtprehisprice.insert_CustHisPrice(pdthisprice, ptid))
//			{
//				logger.error("���ӿͻ���ʷ����ʧ��!");
//				return false;
//			}
//			logger.debug("���ӿͻ���ʷ���ݳɹ���");
//			String begintime = ((Qut_pdtPreHis)pdthisprice.get(0)).getBeginTime();
//			String endtime = ((Qut_pdtPreHis)pdthisprice.get(0)).getEndTime();
//			if (!pdtprehisprice.del_CustPrice(begintime, endtime, ptid))
//			{
//				logger.error("ɾ���ͻ���ǰ������ʧ�ܣ�");
//				return false;
//			} else
//			{
//				logger.debug("ɾ���ͻ���ǰ�����ݳɹ���");
//				return true;
//			}
//		} else
//		{
//			return true;
//		}
//	}
//
//	public boolean dealbrnchhisprice(String ptid, List pdthisprice)
//	{
//		if (pdthisprice.size() > 0)
//		{
//			if (!pdtprehisprice.insert_BranchHisPrice(ptid, pdthisprice))
//			{
//				logger.error("���ӷ�����ʷ����ʧ�ܣ�");
//				return false;
//			}
//			logger.debug("���ӷ�����ʷ���ݳɹ���");
//			String begintime = ((Qut_pdtPreHis)pdthisprice.get(0)).getBeginTime();
//			String endtime = ((Qut_pdtPreHis)pdthisprice.get(0)).getEndTime();
//			if (!pdtprehisprice.del_BranchPrice(begintime, endtime, ptid))
//			{
//				logger.error("ɾ�����е�ǰ������ʧ�ܣ�");
//				return false;
//			} else
//			{
//				logger.debug("ɾ�����е�ǰ�����ݳɹ���");
//				return true;
//			}
//		} else
//		{
//			return true;
//		}
//	}
//
//	public boolean dealjshhisprice(List pdthisprice)
//	{
//		if (pdthisprice.size() > 0)
//		{
//			if (!pdtprehisprice.insert_CustHisPrice(pdthisprice, "P004"))
//			{
//				logger.error("���ӽ��ۻ���ʷ����ʧ�ܣ�");
//				return false;
//			}
//			logger.debug("���ӽ��ۻ���ʷ���ݳɹ���");
//			for (int i = 0; i < pdthisprice.size(); i++)
//			{
//				Qut_pdtPreHis prehis = (Qut_pdtPreHis)pdthisprice.get(i);
//				String exnm = prehis.getCurrencyPair();
//				String dates = prehis.getBeginTime().substring(0, 8);
//				if (!pdtprehisprice.delJSHPrice(exnm, dates))
//				{
//					logger.error("ɾ�����ۻ���ʷ����ʧ�ܣ�");
//					return false;
//				}
//			}
//
//			logger.debug("ɾ�����ۻ���ʷ���ݳɹ���");
//			return true;
//		} else
//		{
//			return true;
//		}
//	}
//
//	public boolean doDealJSHHisPrice(String begintime, String endtime)
//	{
//		HashMap exnmmap = ReadHisExnm.getIntance().getExnmmap();
//		List exnmlist = (List)exnmmap.get("P004");
//		jshHislist = new ArrayList();
//		for (int i = 0; i < exnmlist.size(); i++)
//		{
//			Qut_pdtPreHis hisprice = new Qut_pdtPreHis();
//			hisprice.setBeginTime(begintime);
//			hisprice.setEndTime(endtime);
//			hisprice.setCurrencyPair((String)exnmlist.get(i));
//			logger.debug((new StringBuilder("�ұ�ԣ�")).append((String)exnmlist.get(i)).toString());
//			if (buildJSHQutPdtPreHis(hisprice))
//			{
//				if (hisprice.getArraySize() > 0)
//					jshHislist.add(hisprice);
//			} else
//			{
//				logger.error("�������ۻ�ת������ʧ�ܣ�");
//				return false;
//			}
//		}
//
//		JSHHisPriceCommand jshcomm = new JSHHisPriceCommand();
//		jshcomm = (JSHHisPriceCommand)CommandExecutor.executeWithTransaction(jshcomm, PersistenceConst.getDBSourceName());
//		if (jshcomm.getReturnFlag() != 0)
//		{
//			logger.debug("��ʷ�۸�ת��ʧ�ܣ�");
//			return false;
//		} else
//		{
//			logger.debug("��ʷ�۸�ת�Ƴɹ���");
//			return true;
//		}
//	}
//
//	public boolean doDealBrnchHisPrice(String begintime, String endtime)
//	{
//		HashMap exnmmap = ReadHisExnm.getIntance().getExnmmap();
//		Set set = exnmmap.keySet();
//		for (Iterator it = set.iterator(); it.hasNext();)
//		{
//			brnchHislist = new ArrayList();
//			ptid = ((String)it.next()).toString();
//			if (!"P004".equals(ptid))
//			{
//				logger.debug((new StringBuilder("��Ʒ��")).append(ptid).toString());
//				List exnmlist = (List)exnmmap.get(ptid);
//				for (int i = 0; i < exnmlist.size(); i++)
//				{
//					Qut_pdtPreHis hisprice = new Qut_pdtPreHis();
//					hisprice.setBeginTime(begintime);
//					hisprice.setEndTime(endtime);
//					hisprice.setCurrencyPair((String)exnmlist.get(i));
//					logger.debug((new StringBuilder("�ұ�ԣ�")).append((String)exnmlist.get(i)).toString());
//					if (buildQutPdtPreHis(ptid, hisprice, true))
//					{
//						if (hisprice.getArraySize() > 0)
//							brnchHislist.add(hisprice);
//					} else
//					{
//						logger.error("��������ת������ʧ�ܣ�");
//						return false;
//					}
//				}
//
//				BrnchHisPriceCommand hiscomm = new BrnchHisPriceCommand();
//				hiscomm = (BrnchHisPriceCommand)CommandExecutor.executeWithTransaction(hiscomm, PersistenceConst.getDBSourceName());
//				if (hiscomm.getReturnFlag() != 0)
//				{
//					logger.error("������ʷ�۸�ת��ʧ�ܣ�");
//					return false;
//				}
//			}
//		}
//
//		logger.info("������ʷ�۸�ת�Ƴɹ���");
//		return true;
//	}
//
//	public boolean doDealCustHisPrice(String begintime, String endtime)
//	{
//		HashMap exnmmap = ReadHisExnm.getIntance().getExnmmap();
//		Set set = exnmmap.keySet();
//		for (Iterator it = set.iterator(); it.hasNext();)
//		{
//			custHislist = new ArrayList();
//			ptid = ((String)it.next()).toString();
//			if (!"P004".equals(ptid))
//			{
//				logger.debug((new StringBuilder("��Ʒ��")).append(ptid).toString());
//				List exnmlist = (List)exnmmap.get(ptid);
//				for (int i = 0; i < exnmlist.size(); i++)
//				{
//					Qut_pdtPreHis hisprice = new Qut_pdtPreHis();
//					hisprice.setBeginTime(begintime);
//					hisprice.setEndTime(endtime);
//					hisprice.setCurrencyPair((String)exnmlist.get(i));
//					logger.debug((new StringBuilder("�ұ�ԣ�")).append((String)exnmlist.get(i)).toString());
//					if (buildQutPdtPreHis(ptid, hisprice, false))
//					{
//						if (hisprice.getArraySize() > 0)
//							custHislist.add(hisprice);
//					} else
//					{
//						logger.error("�����ͻ�ת������ʧ�ܣ�");
//						return false;
//					}
//				}
//
//				CustHisPriceCommand hiscomm = new CustHisPriceCommand();
//				hiscomm = (CustHisPriceCommand)CommandExecutor.executeWithTransaction(hiscomm, PersistenceConst.getDBSourceName());
//				if (hiscomm.getReturnFlag() != 0)
//				{
//					logger.error("�ͻ���ʷ�۸�ת��ʧ�ܣ�");
//					return false;
//				}
//			}
//		}
//
//		logger.info("�ͻ���ʷ�۸�ת�Ƴɹ���");
//		return true;
//	}
//
//	public boolean doDealHisPrice(String begintime, String endtime)
//	{
//		return doDealBrnchHisPrice(begintime, endtime) && doDealCustHisPrice(begintime, endtime);
//	}
//
//	public void getPrice(SelHisPricePoint shpp)
//	{
//		logger.info("ȡ��ǰ���е�����...");
//		Qut_pdtPreHis pdtprehis = new Qut_pdtPreHis();
//		pdtprehis.setCurrencyPair(shpp.getCurrencyPair());
//		pdtprehis.setBeginTime(shpp.getBegintime());
//		pdtprehis.setEndTime(shpp.getEndtime());
//		List price = null;
//		if ("P004".equals(shpp.getProductid()))
//			price = pdtprehisprice.sel_JSHPrice(shpp);
//		else
//		if ("0".equals(shpp.getPriceType()))
//			price = pdtprehisprice.getBrnchPrice(shpp.getProductid(), pdtprehis);
//		else
//			price = pdtprehisprice.getCustPrice(shpp.getProductid(), pdtprehis);
//		if (price.size() > 0)
//		{
//			for (int j = 0; j < price.size(); j++)
//			{
//				Price p = (Price)price.get(j);
//				pricelist.add(p);
//			}
//
//		}
//	}
//
//	public void getPrePrice(SelHisPricePoint shpp, List pdtprehislist)
//	{
//		if (pdtprehislist.size() > 0)
//		{
//			for (int i = 0; i < pdtprehislist.size(); i++)
//			{
//				List pc = getPriceFormByte(((Qut_pdtPreHis)pdtprehislist.get(i)).getHisprice());
//				for (int j = 0; j < pc.size(); j++)
//				{
//					Price p = (Price)pc.get(j);
//					if (shpp.getBegintime().compareTo(p.getUpdateTime()) < 0 && shpp.getEndtime().compareTo(p.getUpdateTime()) > 0)
//						pricelist.add(p);
//				}
//
//			}
//
//		}
//	}
//
//	public void getPreHisPrice(SelHisPricePoint shpp)
//	{
//		logger.info("ȡѹ�����е�����...");
//		List pdtprehislist = null;
//		if ("0".equals(shpp.getPriceType()))
//			pdtprehislist = pdtprehisprice.sel_PdtPreHisBrnchPrice(shpp);
//		else
//			pdtprehislist = pdtprehisprice.sel_PdtPreHisCustPrice(shpp);
//		getPrePrice(shpp, pdtprehislist);
//	}
//
//	public void getPreTempPrice(SelHisPricePoint shpp)
//	{
//		logger.info("ȡ��ʱ���е�����...");
//		List pdtprehislist = null;
//		if ("0".equals(shpp.getPriceType()))
//			pdtprehislist = pdtprehisprice.sel_PdtPreHisTempBrnchPrice(shpp);
//		else
//			pdtprehislist = pdtprehisprice.sel_PdtPreHisTempCustPrice(shpp);
//		getPrePrice(shpp, pdtprehislist);
//	}
//
//	public List getPriceByTime(SelHisPricePoint shpp)
//	{
//		getPrice(shpp);
//		getPreHisPrice(shpp);
//		if (pricelist.size() == 0)
//		{
//			logger.debug("��Ҫ������ʷ�۸�");
//			pricelist = ImportHisPrice.importHisPrice(shpp);
//			if (pricelist.size() == 0)
//				getPreTempPrice(shpp);
//		}
//		return pricelist;
//	}
//
//	public ReturnHisPrice getPriceByPageNo(SelHisPricePoint shpp)
//	{
//		ReturnHisPrice rhp = new ReturnHisPrice();
//		List allprice = getPriceByTime(shpp);
//		rhp.setSelpricecount(allprice.size());
//		List pricelist = new ArrayList();
//		for (int i = (shpp.getPageNo() - 1) * shpp.getNumPerPage(); i < shpp.getNumPerPage() * shpp.getPageNo(); i++)
//			if (i < allprice.size())
//				pricelist.add((Price)allprice.get(i));
//
//		rhp.setPricelist(pricelist);
//		return rhp;
//	}
//
//	public boolean del_TempPrice()
//	{
//		HashMap exnmmap = ReadHisExnm.getIntance().getExnmmap();
//		Set set = exnmmap.keySet();
//		for (Iterator it = set.iterator(); it.hasNext();)
//		{
//			ptid = ((String)it.next()).toString();
//			if ("P004".equals(ptid))
//			{
//				if (!pdtprehisprice.del_PreCustTempPrice(ptid))
//				{
//					logger.debug("������ۻ���ʱ��ʧ�ܣ�");
//					return false;
//				}
//			} else
//			if (!pdtprehisprice.del_PreBrnchTempPrice(ptid) || !pdtprehisprice.del_PreCustTempPrice(ptid))
//			{
//				logger.debug("����ʵ��ֽ�ƽ���ʱ��ʧ�ܣ�");
//				return false;
//			}
//		}
//
//		logger.debug("������ʱ��ɹ���");
//		return true;
//	}

//}
