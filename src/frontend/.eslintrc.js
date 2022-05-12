module.exports = {
	env: {
		browser: true,
		es2021: true,
	},
	extends: ['plugin:vue/essential', 'standard'],
	parserOptions: {
		ecmaVersion: 12,
		parser: '@typescript-eslint/parser',
		sourceType: 'module',
	},
	plugins: ['vue', '@typescript-eslint'],
	rules: {
		// 'vue/multi-word-component-names': ["error", {
		// "ignores": ["default", "Login", "Footer", "Header", "Datepicker"]
		// }]
		lintOnSave: false,
		'vue/multi-word-component-names': 'off',
		'vue/no-multiple-template-root': 0,
		quotes: ['off', 'single'],
		'space-before-function-paren': ['error', 'ignore'],
		// or
		'space-before-function-paren': [
			'error',
			{
				anonymous: 'ignore',
				named: 'ignore',
				asyncArrow: 'ignore',
			},
		],
	},
};
