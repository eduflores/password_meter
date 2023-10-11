package com.netdeal.passwordMeter.service;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.netdeal.passwordMeter.dtos.PasswordStrengthDTO;
import com.netdeal.passwordMeter.model.UserStatus;

@Service
public class PasswordStrengthService {

	private boolean hasMinimumLength;
	private boolean hasUppercase;
	private boolean hasLowercase;
	private boolean hasNumbers;
	private boolean hasSymbols;

	public PasswordStrengthDTO calculatePasswordStrength(String password) {
		System.out.println("Encoded Base64 Password " + password);
		byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedPassword = new String(decodedBytes);
		System.out.println("Decoded Base64 Password "+ decodedPassword);
		PasswordStrengthDTO dto = new PasswordStrengthDTO(decodedPassword.toString());
		int strength = calculateStrength(password);
		dto.setStrength(strength);
		UserStatus status = calculateStatus(strength);
		dto.setStatus(status);
		return dto;
	}

	private UserStatus calculateStatus(int strength) {
		UserStatus status;
		
		if (strength >= 0 && strength < 20) {
			status = UserStatus.MUITO_FRACA;
		} else if (strength >= 20 && strength < 40) {
			status = UserStatus.FRACA;
		}  else if (strength >= 40 && strength < 60) {
			status = UserStatus.BOA;
		} else if (strength >= 60 && strength < 80) {
			status = UserStatus.FORTE;
		} else {
			status = UserStatus.MUITO_FORTE;
		}
			
		return status;
	}

	public int calculateStrength(String password) {
		int strength = 0;

		// Calculate hasMinimumLength
		if (password.length() >= 8) {
			this.hasMinimumLength = true;
		}

		// Calculate hasNumbers
		this.hasNumbers = password.matches(".*\\d.*");

		strength += quantityOfCharacters(password);

		strength += uppercaseLetters(password);

		strength += lowercaseLetters(password);

		strength += quantityOfNumbers(password);

		strength += quantityOfSymbols(password);

		strength += middleNumbersOrSymbols(password);

		strength += passwordRequirements(password);
		
		strength -= lettersOnly(password);
		
		strength -= numbersOnly(password);
		
		strength -= repeatCharacters(password);
		
		strength -= consecutiveUppercaseLetters(password);
		
		strength -= consecutiveLowercaseLetters(password);
		
		strength -= consecutiveNumbers(password);
		
//		strength -= sequentialLetters(password);
//		
//		strength -= sequentialNumbers(password);
//		
//		strength -= sequentialSymbols(password);

		if (strength > 100) {
			strength = 100;
		} else if (strength < 0) {
			strength = 0;
		}
		System.out.println("Score Total += " + strength);
		return strength;
	}

	public int quantityOfCharacters(String password) {
		// Adds a FLAT value
		System.out.println("quantityOfCharacters += " + (password.length() * 4));
		return password.length() * 4;
	}

	public int uppercaseLetters(String password) {
		int uppercaseLetters = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[A-Z]").matcher(password);

		// Adds value ONLY IF CONDITION IS MET, condition is one number occurrence
		if (!this.hasNumbers) {
			return uppercaseLetters;
		}

		while (matcher.find()) {
			uppercaseLetters++;
		}
		
		// Calculate hasUppercase
		if (uppercaseLetters == 0) {
			System.out.println("uppercaseLetters += " + strength);
			return strength;
		}

		this.hasUppercase = true;
		strength = (password.length() - uppercaseLetters) * 2;
		System.out.println("uppercaseLetters += " + strength);
		return strength;
	}

	public int lowercaseLetters(String password) {
		int lowercaseLetters = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[a-z]").matcher(password);

		// Adds value ONLY IF CONDITION IS MET, condition is one number occurrence
		if (!this.hasNumbers) {
			return lowercaseLetters;
		}

		while (matcher.find()) {
			lowercaseLetters++;
		}
		
		// Calculate hasLowercase
		if (lowercaseLetters == 0) {
			System.out.println("lowercaseLetters += " + strength);
			return strength;
		}

		this.hasLowercase = true;
		strength = (password.length() - lowercaseLetters) * 2;
		System.out.println("lowercaseLetters += " + strength);
		return strength;
	}

	public int quantityOfNumbers(String password) {
		int quantityOfNumbers = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("\\d").matcher(password);

		// Adds value ONLY IF CONDITION IS MET, condition is one letter occurrence
		if (!this.hasUppercase && !this.hasLowercase) {
			System.out.println("quantityOfNumbers += " + strength);
			return strength;
		}

		while (matcher.find()) {
			quantityOfNumbers++;
		}

		strength = quantityOfNumbers * 4;
		System.out.println("quantityOfNumbers += " + strength);
		return strength;
	}

	public int quantityOfSymbols(String password) {
		// Adds a FLAT value
		int quantityOfSymbols = 0;
		int strength = 0;
		for (int i = 0; i < password.length(); i++) {
			Character character = password.charAt(i);
			if (character >= 65 && character <= 90) {
				continue;
			}
			if (character >= 97 && character <= 122) {
				continue;
			}
			if (character >= 48 && character <= 57) {
				continue;
			} else {
				quantityOfSymbols++;
			}
		}
		
		// Calculate hasLowercase
		if (quantityOfSymbols == 0) {
			System.out.println("quantityOfSymbols += " + strength);
			return strength;
		}

		this.hasSymbols = true;		
		strength = quantityOfSymbols * 6;
		System.out.println("quantityOfSymbols += " + strength);
		return strength;
	}

	public int middleNumbersOrSymbols(String password) {
		// Adds a FLAT value
		int middleNumbersOrSymbols = 0;
		int strength = 0;
		for (int i = 1; i < password.length()-1; i++) {
			Character character = password.charAt(i);
			if (character >= 65 && character <= 90) {
				continue;
			}
			if (character >= 97 && character <= 122) {
				continue;
			}
			if (character >= 48 && character <= 57) {
				middleNumbersOrSymbols++;
			} else {
				middleNumbersOrSymbols++;
			}

		}
		strength = middleNumbersOrSymbols * 2;
		System.out.println("middleNumbersOrSymbols += " + strength);
		return strength;
	}

	public int passwordRequirements(String password) {
		int requirements = 0;
		int strength = 0;
		
		if (this.hasMinimumLength) {
			requirements++;
			
			if (this.hasUppercase) {
				requirements++;
			}
			
			if (this.hasLowercase) {
				requirements++;
			}
			
			if (this.hasNumbers) {
				requirements++;
			}
			
			if (this.hasSymbols) {
				requirements++;
			}
		}
		
		if (requirements <= 3) {
			System.out.println("requirements += " + strength);
			return strength;
		}

		strength = requirements * 2;
		System.out.println("requirements += " + strength);
		return strength;
	}
	
	public int lettersOnly(String password) {
		int lettersOnly = 0;
		Matcher matcher = Pattern.compile("[A-Za-z]").matcher(password);
		
		if (this.hasNumbers || this.hasSymbols) {
			System.out.println("lettersOnly -= " + lettersOnly);
			return lettersOnly;
		}
		
		while (matcher.find()) {
			lettersOnly++;
		}
		
		System.out.println("lettersOnly -= " + lettersOnly);
		return lettersOnly;		
	}
	
	public int numbersOnly(String password) {
		int numbersOnly = 0;
		Matcher matcher = Pattern.compile("[0-9]").matcher(password);
		
		if (this.hasUppercase || this.hasLowercase || this.hasSymbols) {
			System.out.println("numbersOnly -= " + numbersOnly);
			return numbersOnly;
		}
		
		while (matcher.find()) {
			numbersOnly++;
		}
		
		System.out.println("numbersOnly -= " + numbersOnly);
		return numbersOnly;		
	}
	
	/*
	 * Essa lógica não aparenta retornar uma Progressão Aritimética ou Progressão
	 * Geométrica do número de ocorrências. Por isso não foi implementada.
	 */
	public int repeatCharacters(String password) {
		return password.length() * 0;
	}
	
	public int consecutiveUppercaseLetters(String password) {
		int consecutiveUppercaseLetters = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[A-Z]+").matcher(password);
		
		while (matcher.find()) {
			consecutiveUppercaseLetters += matcher.group().length() - 1;
		}
		
		strength = consecutiveUppercaseLetters * 2;
		System.out.println("consecutiveUppercaseLetters -= " + strength);
		return strength;		
	}
	
	public int consecutiveLowercaseLetters(String password) {
		int consecutiveLowercaseLetters = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[a-z]+").matcher(password);
		
		while (matcher.find()) {
			consecutiveLowercaseLetters += matcher.group().length() - 1;
		}
		
		strength = consecutiveLowercaseLetters  * 2;
		System.out.println("consecutiveLowercaseLetters -= " + strength);
		return strength;		
	}
	
	public int consecutiveNumbers(String password) {
		int consecutiveNumbers = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[0-9]+").matcher(password);
		
		while (matcher.find()) {
			consecutiveNumbers += matcher.group().length() - 1;
		}
		
		strength = consecutiveNumbers * 2;
		System.out.println("consecutiveNumbers -= " + strength);
		return strength;		
	}
	
	public int sequentialLetters(String password) {
		int sequentialLetters = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[A-Za-z]*").matcher(password);
		
		while (matcher.find()) {
			sequentialLetters++;
		}
		
		if (sequentialLetters < 3) {
			System.out.println("sequentialLetters -= " + strength);
			return strength;
		}
		
		strength = (sequentialLetters -2) * 3;
		System.out.println("sequentialLetters -= " + strength);
		return strength;		
	}
	
	public int sequentialNumbers(String password) {
		int sequentialNumbers = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[0-9]*").matcher(password);
		
		while (matcher.find()) {
			sequentialNumbers++;
		}
		
		if (sequentialNumbers < 3) {
			System.out.println("sequentialNumbers -= " + strength);
			return strength;
		}
		
		strength = (sequentialNumbers -2) * 3;
		System.out.println("sequentialNumbers -= " + strength);
		return strength;
	}
	
	public int sequentialSymbols(String password) {
		int sequentialSymbols = 0;
		int strength = 0;
		Matcher matcher = Pattern.compile("[^A-Za-z0-9]*").matcher(password);
		
		while (matcher.find()) {
			sequentialSymbols++;
		}
		
		if (sequentialSymbols < 3) {
			System.out.println("sequentialSymbols -= " + strength);
			return strength;
		}
		
		strength = (sequentialSymbols -2) * 3;
		System.out.println("sequentialSymbols -= " + strength);
		return strength;		
	}

}
