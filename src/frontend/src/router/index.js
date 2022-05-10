import {createRouter, createWebHistory} from 'vue-router'


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
    component: () => import('@/components/user/Signup.vue')
  },
  {
    path: '/mAdmin',
    name: 'mAdmin',
    component: () => import('@/components/user/Admin.vue')
  },
  {
    path: '/company',
    name: 'Company',
    component: () => import('@/components/user/Company.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/components/user/Login.vue')
  },
  {
    path: '/memberupdate',
    name: 'MemberData',
    component: () => import('@/components/user/MemberData.vue')
  },
  {
    path: '/horby',
    name: 'Horby',
    component: () => import('@/components/user/Horby.vue')
  },
  {
    path:'/agreement',
    name:'Agreement',
    component: () => import('@/components/user/Agreement.vue')
  },
  {
    path:'/sellerAccept',
    name:'SellerAccept',
    component: () => import('@/components/user/SellerAccept.vue')
  },
  {
    path:'/searchSelect',
    name:'SearchSelect',
    component: () => import('@/components/user/SearchSelect.vue')
  },
  {
    path:'/searchId',
    name:'SearchId',
    component: () => import('@/components/user/IdSearch.vue')
  },
  {
    path:'/searchPass',
    name:'SearchPass',
    component: () => import('@/components/user/PassSearch.vue')
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
    path: '/view/:writer_code',
    name: 'View',
    component: () => import('@/components/board/View.vue')
  },
  {
    path: '/update/:writer_code',
    name: 'Update',
    component: () => import('@/components/board/Update.vue')
  },
  {
    path: '/tbcreate',
    name: 'TBCreate',
    component: () => import('@/components/board/TBCreate.vue')

  },
  {
    path: '/teamboard',
    name: 'TeamBoard',
    component: () => import('@/components/board/TeamBoard.vue')

  },
  {
    path: '/tbview/:teamwriter_code',
    name: 'TBView',
    component: () => import('@/components/board/TBView.vue')
  },
  {
    path: '/tbupdate/:teamwriter_code',
    name: 'TBUpdate',
    component: () => import('@/components/board/TBUpdate.vue')
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
    path: '/itemBuy/buyList/:buyId',
    name: 'BuyDetailList',
    component: () => import('@/components/cart/ItemBuy/BuyDetailList.vue'),
    props: true
  },
  {
    path: '/itemBuy/buyNow/:menuid',
    name: 'BuyNow',
    component: () => import('@/components/cart/ItemBuy/BuyNow.vue'),
    props: true
  },
  {
    path: '/itemBuy/buyComplete',
    name: 'BuyComplete',
    component: () => import('@/components/cart/ItemBuy/BuyComplete.vue')
  },
  {
    path: '/itemShare',
    name: 'ShareList',
    component: () => import('@/components/cart/ItemShare/ShareList.vue')
  },
  {
    path: '/itemShare/shareNow',
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
    path: '/infoter/infoterNow',
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
    path: '/cart/buy',
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
    //////////////////////////////보관함관리/////////////////////////////////

  {
    path: "/storageAdmin",
    name: "admin",
    component: ()=> import('@/views/admin/StorageAdminView.vue')
  },
  {
    path: "/storage",
    name: "storage",
    component: ()=> import('@/views/admin/storage/StorageView.vue')
  },
  {
    path: "/manager",
    name: "manager",
    component: ()=> import('@/views/admin/manager/StorageManagerView.vue')
  },
  {
    path: "/storageView",
    name:"userStorage",
    component: ()=> import('@/views/user/UserStorageView.vue')
  },
  {
    path: "/storageView/:storageCode",
    name:"userStorageDetail",
    component: ()=> import('@/components/storageService/user/UserStorageDetail.vue')
  },
  {
    path:"/storageView/:storageCode/paypage",
    name:"storagePay",
    component:()=> import('@/views/user/StoragePayPage.vue')
  },
  {
    path: "/myBox",
    name: "myBox",
    component:()=>import('@/views/user/MyStorageBox.vue')
  },
  {
    path: "/myBox/renewalBox",
    name: "renewalBox",
    component:()=>import('@/components/storageService/user/RenewalBox.vue')
  },
  {
    path: "/myBox/moveBox",
    name: "moveBox",
    component:()=>import('@/components/storageService/user/MoveBox.vue')
  },
  {
    path: "/myBox/moveBox/boxToBox",
    name: "StorageMoveBoxDetail",
    component:()=>import('@/components/storageService/user/StorageMoveBoxDetail.vue')
  },
  {
    path: "/myBox/moveBox/storagePay",
    name: "BoxToBoxMovePay",
    component:()=>import('@/views/user/BoxToBoxMovePay.vue'),
    props: true
  },
  {
    path: "/myBox/moveBox/roundPay",
    name: "roundToBoxMovePay",
    component:()=>import('@/views/user/roundToBoxMovePay.vue'),
    props: true
  },
  {
    path: "/myBox/repairBox",
    name: "repairBox",
    component:()=>import('@/components/storageService/user/RepairBox.vue')
  },
  {
    path:'/storageManager',
    name:'storageManager',
    component: ()=> import('@/views/manager/ManagerStorage.vue'),
    children:[
      {
        path: ":storage",
        name: "boxDetail",
        component: ()=> import('@/components/storageService/manager/BoxModalDetail.vue')
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


]

export const router = createRouter({
  history: createWebHistory(),
  routes
})
