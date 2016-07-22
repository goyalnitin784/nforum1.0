package com.nforum.platform.util;

public interface Constants {
	
	final static String BussinessPlatform ="nforum.bussiness.platform";

	long InvalidId=0L;
	
	interface B2B {

		final String SSOTOKEN = "ssoToken";
		final String METHOD = "method";
		final String APPLICATION = "application";
		final String APPLICATION_TYPE_WEB = "web";
		final String MISSED_SAVING_DETAILS= "msdt";
		final String ENGAGEMENT_NO = "engagementNo";

		interface DiplayMarkUp{
			final String DSMint ="outbound_flight";
			final String DSMdom="dom_flight";
			final String DSM  = "dsm";
			final String DTM = "dstmr";
			final String SDTM="sdstmr";
		}
		
		interface Methods{
			
			final String GET_PAYMENT_OPTIONS = "getPaymentOptions";
			final String GET_CORPORATE_USER = "getCorporateUser";
			final String GET_CORPORATE_USER_LIST = "getCorporateUserList";
			final String GET_CORP_USER_LIST = "getCorpUserList";
			final String SAVE_MISSED_SAVING_DETAILS = "smsdt";
			final String GET_PASSTHROUGH_GRID = "getPassthrough";
			final String GET_AGENT_PROFILE = "getAgentProfile";
			final String VALIDATE_SSOTOKEN = "validatetokenid";
			final String GET_ENGAGEMENT_LIST = "getEngagementList";

			interface HOTELS{
				
				final String HOTEL_SEARCH = "hotelSearch";
			
			}
			
		}

	}
}
