import { createRouter, createWebHistory } from 'vue-router';

const routes = [
	  /////////////////////////////회원관리///////////////////////////////
  {
    path: '/',
    name: 'Main',
    component: () => import('@/components/HelloWorld.vue')
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('@/components/user/singUp/Signup.vue')
  },
  {
    path: '/mAdmin',
    name: 'mAdmin',
    component: () => import('@/components/user/adminManage/Admin.vue')
  },
  {
    path: '/company',
    name: 'Company',
    component: () => import('@/components/user/Company.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/components/user/singUp/Login.vue')
  },
  {
    path: '/memberupdate',
    name: 'MemberData',
    component: () => import('@/components/user/singUp/MemberData.vue')
  },
  {
    path: '/horby',
    name: 'Horby',
    component: () => import('@/components/user/Horby/Horby.vue')
  },
  {
    path:'/agreement',
    name:'Agreement',
    component: () => import('@/components/user/singUp/Agreement.vue')
  },
  {
    path:'/sellerAccept',
    name:'SellerAccept',
    component: () => import('@/components/user/adminManage/SellerAccept.vue')
  },
  {
    path:'/searchSelect',
    name:'SearchSelect',
    component: () => import('@/components/user/memberSearch/SearchSelect.vue')
  },
  {
    path:'/searchId',
    name:'SearchId',
    component: () => import('@/components/user/memberSearch/IdSearch.vue')
  },
  {
    path:'/searchPass',
    name:'SearchPass',
    component: () => import('@/components/user/memberSearch/PassSearch.vue')
  },
  {
    path:'/changePass',
    name:'changePass',
    component: () => import('@/components/user/singUp/PassChange.vue')
  },
  {
    path:'/myPage',
    name:'mypage',
    component: () => import('@/components/user/myPage/MyPage.vue')
  },
  {
    path:'/myCompanyPage',
    name:'myCompanyPage',
    component: () => import('@/components/user/myPage/MyCompany.vue')
  },
  {
    path:'/myPageEquip',
    name:'myPageEquip',
    component: () => import('@/components/user/myPage/MyEquipmentPage.vue')
  },
  {
    path:'/myPageWritter',
    name:'myPageWritter',
    component: () => import('@/components/user/myPage/MyWritter.vue')
  },
  {
    path:'/myPageComment',
    name:'myPageComment',
    component: () => import('@/components/user/myPage/MyCommentList.vue')
  },
  {
    path:'/myPageOrder',
    name:'myPageOrder',
    component: () => import('@/components/user/myPage/MyOrder.vue')
  },
  {
    path:'/myPreference',
    name:'myPreference',
    component: () => import('@/components/user/myPage/MyPreference.vue')
  },
  {
    path:'/memberAdmin',
    name:'memberAdmin',
    component: () => import('@/components/user/adminManage/MemberManagementAdmin.vue')
  },
  {
    path:'/orderAdmin',
    name:'orderAdmin',
    component: () => import('@/components/user/adminManage/OrderManagement.vue')
  },
  {
    path:'/writerAdmin',
    name:'writerAdmin',
    component: () => import('@/components/user/adminManage/WriterManagement.vue')
  },
  {
    path:'/addEquipment',
    name:'addEquipment',
    component: () => import('@/components/user/myPage/AddEquip.vue')
  },
  {
    path:'/updateEquipment/:equipCode',
    name:'updateEquipment',
    component: () => import('@/components/user/myPage/MyEquipUpdate.vue')
  },
  {
    path:'/memberData/:mid',
    name:'memberData',
    component: () => import('@/components/user/adminManage/MemberAdminData.vue')
  },
  {
    path:'/companyData/:mid',
    name:'companyData',
    component: () => import('@/components/user/adminManage/CompanyAdminData.vue')
  },

    //////////////////////////////게시판////////////////////////////////////

  {
    path: '/board',
    name: 'Board',
    component: () => import('@/components/board/Board.vue')
  },
  {
    path: '/read',
    name: 'Read',
    component: () => import('@/components/board/Read.vue')
  },
  {
    path: '/create/:contentId?',
    name: 'Create',
    component: () => import('@/components/board/Create.vue')
  },
  {
    path: '/create',
    name: 'Create',
    component: () => import('@/components/board/Create.vue')
  },
  {
    path: '/view/:boardId',
    name: 'View',
    component: () => import('@/components/board/View.vue')
  },
  {
    path: '/update/:writer_code',
    name: 'Update',
    component: () => import('@/components/board/Update.vue')
  },
  {
    path: '/noticeboard',
    name: 'Noticeboard',
    component: () => import('@/components/board/NoticeBoard.vue')
  },
  {
    path: '/noticedetail/:boardId',
    name: 'Noticedetail',
    component: () => import('@/components/board/NoticeDetail.vue')
  },


    ///////////////////////////////상품관리///////////////////////////////////

  {
    path: '/ProductMain',
    name: 'ProductMain',
    component: () => import('@/components/product/ProductMain.vue')
  },
  {
    path: '/ProductPage',
    name: 'ProductPage',
    component: () => import('@/components/product/ProductPage.vue')
  },
    ////////////////////////////////////////////////////
  {
    path: '/ProductBuy',
    name: 'ProductBuy',
    component: () => import('@/components/product/Registration/RegistrationBuy.vue')
  },
  {
    path: '/ProductRental',
    name: 'ProductRental',
    component: () => import('@/components/product/Registration/RegistrationRental.vue')
  },
  {
    path: '/ProductCamping',
    name: 'ProductCamping',
    component: () => import('@/components/product/Registration/RegistrationCamping.vue')
  },
  {
    path: '/RegistrationCampingDetail/:campingName',
    name: 'RegistrationCampingDetail',
    component: () => import('@/components/product/Registration/RegistrationCampingDetail.vue')
  },
    /////////////////////////////////////////////////////////
  {
    path: '/BuyProductList',
    name: 'BuyProductList',
    component: () => import('@/components/product/Buy/BuyProductList.vue')
  },
  {
    path: '/BuyProductDetail/:buyId',
    name: 'BuyProductDetail',
    component: () => import('@/components/product/Buy/BuyProductDetail.vue'),
    props: true
  },
  {
    path: '/BuyProductEdit',
    name: 'BuyProductEdit',
    component: () => import('@/components/product/Buy/BuyProductEdit.vue'),
    props: true,
  },
    ////////////////////////////////////////////////////////////////
  {
    path: '/RentalProductList',
    name: 'RentalProductList',
    component: () => import('@/components/product/Rental/RentalProductList.vue')
  },
  {
    path: '/RentalProductDetail/:rentalId',
    name: 'RentalProductDetail',
    component: () => import('@/components/product/Rental/RentalProductDetail.vue'),
    props: true
  },
  {
    path: '/RentalProductEdit',
    name: 'RentalProductEdit',
    component: () => import('@/components/product/Rental/RentalProductEdit.vue'),
    props: true,
  },
    ///////////////////////////////////////////////////////////////////
  {
    path: '/CampingProductList',
    name: 'CampingProductList',
    component: () => import('@/components/product/Camping/CampingProductList.vue')
  },
  {
    path: '/CampingProductDetail/:campingId',
    name: 'CampingProductDetail',
    component: () => import('@/components/product/Camping/CampingProductDetail.vue'),
    props: true
  },
  {
    path: '/CampingProductEdit/:campingId',
    name: 'CampingProductEdit',
    component: () => import('@/components/product/Camping/CampingProductEdit.vue'),
    props: true,
  },
  ///////////////////////////////////////////////////////////////////
  {
    path: '/RoomProductList/:campingId',
    name: 'RoomProductList',
    component: () => import('@/components/product/Room/RoomProductList.vue'),
    props: true,
  },
  {
    path: '/RoomProductDetail/:detailId',
    name: 'RoomProductDetail',
    component: () => import('@/components/product/Room/RoomProductDetail.vue'),
    props: true
  },
  {
    path: '/RoomProductEdit',
    name: 'RoomProductEdit',
    component: () => import('@/components/product/Room/RoomProductEdit.vue'),
    props: true,
  },
  {
    path: '/RoomProductCreate/:campingId',
    name: 'RoomProductCreate',
    component: () => import('@/components/product/Room/RoomProductCreate.vue'),
    props: true,
  },
  ///////////////////////////////////////////////////////////////////

  {
    path: '/ProductList',
    name: 'ProductList',
    component: () => import('@/components/product/ProductList.vue')
  },
  {
    path: '/ProductDetail/:menuid',
    name: 'productDetail',
    component: () => import('@/components/product/ProductDetail.vue'),
    props: true
  },

    ////////////////////////주문, 결제/////////////////////////////////
  {
    path: '/itemBuy',
    name: 'ItemBuy',
    component: () => import('@/components/cart/ItemBuy/BuyList.vue')
  },
  {
    path: '/itemBuy/index',
    name: 'ItemBuyIndex',
    component: () => import('@/components/cart/ItemBuy/Buyindex.vue')
  },
  {
    path: '/itemBuy/buyList/:buyId',
    name: 'BuyDetailList',
    component: () => import('@/components/cart/ItemBuy/BuyDetailList.vue'),
    props: true
  },
  {
    path: '/itemBuy/buyNow/:buyId',
    name: 'BuyNow',
    component: () => import('@/components/cart/ItemBuy/BuyNow.vue'),
    props: true
  },
  {
    path: '/itemBuy/buyComplete',
    name: 'BuyComplete',
    component: () => import('@/components/cart/ItemBuy/BuyComplete.vue'),
    props: true
  },
  {
    path: '/itemShare',
    name: 'ShareList',
    component: () => import('@/components/cart/ItemShare/ShareList.vue')
  },
  {
    path: '/itemShare/rentalList/:rentalId',
    name: 'ShareDetailList',
    component: () => import('@/components/cart/ItemShare/ShareDetailList.vue'),
    props: true
  },
  {
    path: '/itemShare/shareNow/:rentalId',
    name: 'ShareNow',
    component: () => import('@/components/cart/ItemShare/ShareNow.vue')
  },
  {
    path: '/itemBuy/shareComplete',
    name: 'shareComplete',
    component: () => import('@/components/cart/ItemShare/ShareComplete.vue')
  },
  {
    path: '/infoter',
    name: 'InfoterList',
    component: () => import('@/components/cart/Infoter/InfoterList.vue')
  },
  {
    path: '/infoter/infoterList/:campingId',
    name: 'InfoterDetailList',
    component: () => import('@/components/cart/Infoter/InfoterDetailList.vue'),
    props: true
  },
  {
    path: '/infoter/infoterBoard',
    name: 'InfoterDetailBoard',
    component: () => import('@/components/cart/Infoter/InfoterDetailBoard.vue'),
  },
  {
    path: '/infoter/infoterupdate/:boardCampingCode',
    name: 'InfoterDetailBoardUpdate',
    component: () => import('@/components/cart/Infoter/InforterDetailBoardUpdate.vue')
  },
  {
    path: '/infoter/infoterNow/:campingId/:detailId',
    name: 'InfoterNow',
    component: () => import('@/components/cart/Infoter/InfoterNow.vue')
  },
  {
    path: '/infoter/infoterComplete',
    name: 'InfoterComplete',
    component: () => import('@/components/cart/Infoter/InfoterNow.vue')
  },
  {
    path: '/cart',
    name: 'CartList',
    component: () => import('@/components/cart/Cart/CartList.vue')
  },
  {
    path: '/cart/buy/:mcode',
    name: 'BuyCart',
    component: () => import('@/components/cart/Cart/BuyCart.vue')
  },
  {
    path: '/cart/share',
    name: 'ShareCart',
    component: () => import('@/components/cart/Cart/ShareCart.vue')
  },
  {
    path: '/cart/reservation',
    name: 'ReservationCart',
    component: () => import('@/components/cart/Cart/ReservationCart.vue')
  },
  {
    path: '/ordersList',
    name: 'Orders',
    component: () => import('@/components/cart/ordersInfo/Orders.vue'),
    props: true
  },
  {
    path: '/ordersList/buyOrders/:mid',
    name: 'BuyOrders',
    component: () => import('@/components/cart/ordersInfo/BuyOrders.vue'),
    props: true
  },
  {
    path: '/ordersList/shareOrders/:mid',
    name: 'ShareOrders',
    component: () => import('@/components/cart/ordersInfo/ShareOrders.vue')
  },
  {
    path: '/ordersList/reservationOrders/:mid',
    name: 'ReservationOrders',
    component: () => import('@/components/cart/ordersInfo/ReservationOrders.vue')
  },
    //////////////////////////////보관함관리/////////////////////////////////

  {
    path: "/storageAdmin",
    name: "admin",
    component: ()=> import('@/views/admin/storage/StorageView.vue')
  },
  {
    path: "/storageAdmin/storageRevise",
    name:"StorageRevise",
    component: ()=> import('@/components/storageService/admin/storage/StorageRevise.vue')
  },
  {
    path: "/storageView",
    name:"userStorage",
    component: ()=> import('@/views/user/UserStorageView.vue')
  },
  {
    path: "/storageViewDetail",
    name:"userStorageDetail",
    component: ()=> import('@/components/storageService/user/UserStorageDetail.vue')
  },
  {
    path:"/storageView/paypage",
    name:"storagePay",
    component:()=> import('@/views/user/StoragePayPage.vue')
  },
  {
    path: "/myBox",
    name: "myBox",
    component:()=>import('@/views/user/MyStorageBox.vue')
  },
  {
    path: "/myBox/renewalBox/:useBoxCode",
    name: "renewalBox",
    component:()=>import('@/components/storageService/user/RenewalBox.vue')
  },
  {
    path: "/myBox/closeBox",
    name: "closeBox",
    component:()=>import('@/components/storageService/user/CloseBox.vue')
  },
  {
    path: "/myBox/moveBox/:useBoxCode",
    name: "moveBox",
    component:()=>import('@/components/storageService/user/MoveBox.vue')
  },
  {
    path: "/myBox/moveBox/:useBoxCode/boxToBox/:storageCode",
    name: "StorageMoveBoxDetail",
    component:()=>import('@/components/storageService/user/StorageMoveBoxDetail.vue')
  },
  {
    path: "/myBox/moveBox/:useBoxCode/:newStorageBoxCode/storagePay",
    name: "BoxToBoxMovePay",
    component:()=>import('@/views/user/BoxToBoxMovePay.vue'),
    props: true
  },
  {
    path: "/myBox/moveBox/:useBoxCode/roundPay",
    name: "roundToBoxMovePay",
    component:()=>import('@/views/user/roundToBoxMovePay.vue'),
    props: true
  },
  {
    path: "/myBox/repairBox/:useBoxCode",
    name: "repairBox",
    component:()=>import('@/components/storageService/user/RepairBox.vue')
  },
  {
    path: "/myBox/repairBox/:useBoxCode/repairPay",
    name: "repairBoxPay",
    component:()=>import('@/views/user/RepairBoxPay.vue')
  },
  {
    path: "/storageComplete",
    name: "storageComplete",
    component:()=>import('@/views/user/StorageComplete.vue')
  },
  {
    path:'/storageManager',
    name:'storageManager',
    component: ()=> import('@/views/manager/ManagerStorage.vue'),
    children:[
      {
        path: ":storage",
        name: "boxDetail",
        component: ()=> import('@/components/storageService/manager/NotUsingInfo.vue')
      }
    ]
  },
	//////////////계획관리//////////////////////
	{
		path: '/TeamManagementPage',
		name: 'teamManage',
		component: () => import('@/components/team/TeamManagementPage'),
	},
	{
		path: '/TeamMember',
		name: 'teamMember',
		component: () => import('@/components/team/TeamMember'),
	},
	{
		path: '/basicPlan',
		name: 'basicPlan',
		component: () => import('@/components/plan/basicPlan'),
	},
	{
		path: '/detailPlan',
		name: 'detailPlan',
		component: () => import('@/components/plan/detailPlan'),
	},
	{
		path: '/selectCampingPlace',
		name: 'selectCampingPlace',
		component: () => import('@/components/plan/selectCampingPlace'),
	},
	{
		path: '/detailPlan/:detailCode',
		name: 'checkDetailPlan',
		component: () => import('@/components/plan/detailPlan'),
	},
	{
		path: '/planList',
		name: 'planList',
		component: () => import('@/components/plan/planList'),
	},
	{
		path: '/selectCopy',
		name: 'selectCopy',
		component: () => import('@/components/plan/selectCopy'),
	},
	{
		path: '/usersPlan',
		name: 'usersPlan',
		component: () => import('@/components/plan/usersPlan'),
	},
	{
		path: '/selectTeam',
		name: 'selectTeam',
		component: () => import('@/components/plan/selectTeam'),
	},
	{
		path: '/insertCopy',
		name: 'insertCopy',
		component: () => import('@/components/plan/insertCopy'),
	},
];

export const router = createRouter({
	history: createWebHistory(),
	routes,
});
