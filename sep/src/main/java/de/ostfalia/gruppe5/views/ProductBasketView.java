package de.ostfalia.gruppe5.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.boundary.OrderDetailService;
import de.ostfalia.gruppe5.business.boundary.OrderService;
import de.ostfalia.gruppe5.business.boundary.PaymentService;
import de.ostfalia.gruppe5.business.boundary.ProductBasket;
import de.ostfalia.gruppe5.business.boundary.validation.IBAN;
import de.ostfalia.gruppe5.business.boundary.validation.IBANValidator;
import de.ostfalia.gruppe5.business.boundary.validation.IBANValidatorHelper;
import de.ostfalia.gruppe5.business.controller.IBANCalculator;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.CustomerUser;
import de.ostfalia.gruppe5.business.entity.Item;
import de.ostfalia.gruppe5.business.entity.Order;
import de.ostfalia.gruppe5.business.entity.OrderDetail;
import de.ostfalia.gruppe5.business.entity.OrderStatus;
import de.ostfalia.gruppe5.business.entity.Payment;
import de.ostfalia.gruppe5.business.entity.Product;

@Named
@RequestScoped
public class ProductBasketView {

	@Inject
	private CustomerUser customerUser;

	private HtmlDataTable datatable;
	@Inject
	private ProductBasket productBasket;
	@Inject
	private CustomerService customerService;
	@Inject
	private OrderService orderService;
	@Inject
	private OrderDetailService orderDetailService;
	@Inject
	private PaymentService paymentService;

	private Order order;
	private OrderDetail orderDetail;
	private Payment payment;
	private LocalDate currentDate;

	private IBANValidatorHelper helper;

	private String iban;

	private Integer accountNumber;
	private Integer blz;

	public ProductBasketView() {
	}

	@PostConstruct
	private void init() {
		Integer userId = customerUser.getId();
		if (userId != null) {

			Customer customer = customerService.find(userId);

			productBasket.setCustomer(customer);
		}
		currentDate = LocalDate.now();

	}

	public String processOrder() {
		if(getIban() != null && getIban().length() > 0){
			helper = new IBANValidatorHelper();
			helper.build(getIban());
			if(!helper.validateIBAN()){
				throw new ValidatorException(new FacesMessage("Invalid IBAN"));
			}
		}else if(getAccountNumber() != null && getBlz() != null){
			setIban(IBANCalculator.calculateDEIBANFromKntnrAndBlz(getAccountNumber().toString(), getBlz().toString()));
			helper = new IBANValidatorHelper();
			helper.build(iban);
			if(!helper.validateIBAN()){
				throw new ValidatorException(
						new FacesMessage("Invalid Combination of Accountnumber and BLZ"));
			}
		}


		this.order = createOrder();
		this.payment = createPayment();

		List<OrderDetail> orderDetails = new ArrayList<>();

		this.orderService.save(order);
		for (Item i : productBasket.getItemList()) {
			OrderDetail singleOrderDetail = new OrderDetail();
			singleOrderDetail.setOrderNumber(order);
			singleOrderDetail.setProductCode(i.getProduct());
			singleOrderDetail.setPriceEach(i.getProduct().getBuyPrice());
			singleOrderDetail.setQuantityOrdered(i.getQuantity());
			singleOrderDetail.setOrderLineNumber((short) 0);
			orderDetails.add(singleOrderDetail);
			this.orderDetailService.save(singleOrderDetail);
		}

		this.paymentService.save(payment);
		return "basket?faces-redirect=true";
	}

	private Order createOrder() {
		Order order = new Order();
		order.setOrderNumber(orderService.nextID());
		order.setOrderDate(currentDate);
		order.setRequiredDate(currentDate);
		order.setStatus(OrderStatus.IN_PROCESS.toString());
		order.setCustomerNumber(this.productBasket.getCustomer());

		return order;
	}

	private Payment createPayment() {
		Payment payment = new Payment();
		payment.setAmount(getTotalPrice());
		payment.setCustomerNumber(this.productBasket.getCustomer());

		payment.setCheckNumber(iban + "_" + order.getOrderNumber().toString());
		payment.setPaymentDate(currentDate);

		return payment;
	}

	public String buy(Product product, int quantity) {
		productBasket.buyProduct(product, quantity);
		return null;
	}

	public String remove() {
		int indexOfList = datatable.getRowIndex();
		productBasket.removeItem(indexOfList);
		return null;
	}

	public String changeQuantity(int newQuantity) {
		int indexOfList = datatable.getRowIndex();
		productBasket.changeQuantityInBasket(indexOfList, newQuantity);
		return null;
	}

	public boolean isValidOrder() {
		return //!(getIban() != null && getIban().length() > 15) && !(getAccountNumber() != null && getBlz() != null) ||
				 productBasket.getItemList().isEmpty() || !(productBasket.getCustomer() != null);
	}

	public void ibanChanged(ValueChangeEvent e){
		if(e.getNewValue() != null){
			setIban(e.getNewValue().toString());
		}
	}

	public void accountNumberChanged(ValueChangeEvent e){
		setAccountNumber(Integer.parseInt(e.getNewValue().toString()));
	}

	public void blzChanged(ValueChangeEvent e){
		setBlz(Integer.parseInt(e.getNewValue().toString()));
	}

	public double getTotalPrice() {
		return productBasket.calulateTotal();
	}

	public ProductBasket getProductBasket() {
		return productBasket;
	}

	public void setProductBasket(ProductBasket productBasket) {
		this.productBasket = productBasket;
	}

	public HtmlDataTable getDatatable() {
		return datatable;
	}

	public void setDatatable(HtmlDataTable datatable) {
		this.datatable = datatable;
	}

	public CustomerUser getCustomerUser() {
		return customerUser;
	}

	public void setCustomerUser(CustomerUser customerUser) {
		this.customerUser = customerUser;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getBlz() {
		return blz;
	}

	public void setBlz(Integer blz) {
		this.blz = blz;
	}
}
