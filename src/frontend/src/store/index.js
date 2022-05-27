import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';

export default createStore({
	state: {
		loginState: null,
		stateCode: null,
		teamName: '',
		mcode: '',
		teamList: [],
		teamMembers: [],
		teamMemberList: [],
		insertName: '',
		teamCode: '',
		diff: 0,
		planCode: '',
		loginedTeamCode: '',
		storage: {},
		timeStorage: {},
		itemStorage:[],
		infoStorage:{},
		CampingIdOfRooms: '',
		member: '',
		moveBoxInfo:{},
	},
	getters: {
		getLoginState(state) {
			return state.loginState;
		},
		getCampingIdState(state) {
			return state.CampingIdOfRooms;
		},
	},
	mutations: {
		setLoginState(state, loginState, stateCode, mcode) {
			state.loginState = loginState;
			state.stateCode = stateCode;
			state.mcode = mcode;
		},
		setLogoutState(state) {
			state.loginState = null;
			state.stateCode = null;
			state.mcode = null;
		},
		updateTeamList(state, teamMember) {
			state.teamList.push(teamMember);
		},
		updateMyTeam(state, member) {
			state.member = member;
		},
		updateTeamName(state, teamName) {
			state.teamName = teamName;
		},
		updateTeamMemberList(state, mname) {
			state.teamMemberList.push(mname);
		},
		updateTeamCode(state, teamCode) {
			state.teamCode = teamCode;
		},
		updateDiff(state, diff) {
			state.diff = diff;
		},
		updatePlanCode(state, planCode) {
			state.planCode = planCode;
		},
		updateLoginedTeamCode(state, loginedTeamCode) {
			state.loginedTeamCode = loginedTeamCode;
		},
		storageCheck(state, storage) {
			state.storage = storage;
			console.log(state.storage);
		},
		storageClear(state) {
			state.storage = {};
			console.log('지워졌나');
			console.log(state.storage);
		},
		putTimeStorage(state, form) {
			state.timeStorage = form;
		},
		putItemStorage(state, form) {
			state.itemStorage = form;
		},
		putInfoStorage(state, form) {
			state.infoStorage = form;
		},
		cartStorageClear(state) {
			state.timeStorage = {};
		},
		setCampingIdState(state, CampingIdOfRooms) {
			state.CampingIdOfRooms = CampingIdOfRooms;
		},
		moveBoxInfo(state, box){
			state.moveBoxInfo = box;
		}
	},
	actions: {},

	plugins: [createPersistedState()],
});
