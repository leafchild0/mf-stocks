module.exports = {
	root: true,
	env: {
		node: true
	},
	extends: [
		'eslint:recommended',
		'plugin:vue/essential',
	],
	rules: {
		'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
		'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
		'no-alert': 'error',
		'no-undef': 'warn',
		'no-empty-function': 'error',
		'no-eval': 'error',
		'block-scoped-var': 'warn',
		complexity: ['error', 10],
		'no-implicit-globals': 'error',
		'no-implied-eval': 'error',
		'no-proto': 'error',
		'require-await': 'error',
		camelcase: ['warn', { properties: 'always' }],
		'max-depth': ['warn', 4],
		'max-len': ['warn', { code: 180, tabWidth: 4 }],
		'max-lines': ['error', 500],
		'max-lines-per-function': ['error', 300],
		'max-nested-callbacks': ['error', 4],
		'max-params': ['warn', 3],
		'max-statements-per-line': ['warn', { max: 1 }],
		'arrow-spacing': 'warn',
		indent: ['warn', 'tab', { SwitchCase: 1 }],
		'vue/script-indent': [
			'warn',
			'tab',
			{
				baseIndent: 0,
				switchCase: 1,
				ignores: []
			}
		],
		'linebreak-style': ['error', 'unix'],
		'no-var': 'warn',
		quotes: ['error', 'single'],
		semi: ['warn', 'always'],
		curly: ['warn', 'multi-line', 'consistent'],
		'brace-style': ['warn', '1tbs', { allowSingleLine: true }],
		'prefer-const': [
			'warn',
			{
				destructuring: 'any',
				ignoreReadBeforeAssign: false
			}
		]
	},
	overrides: [
		{
			files: ['*.vue'],
			rules: {
				indent: 'off'
			}
		}
	],
	parserOptions: {
		parser: 'babel-eslint'
	}
};
