import { createRouter, createWebHistory } from 'vue-router';

const routes = [
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
