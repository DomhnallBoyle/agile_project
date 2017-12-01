using System;
using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;

using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
using System.Text;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for CreateAcceptanceTest.xaml
    /// </summary>
    public partial class CreateAcceptanceTest : BasePage 

    {
        #region Private Variables

        private UserStoryClient _client;

        private Style _invalidTextBoxStyle;
        private Style _validTextBoxStyle;

        private String _warningMessage;
        #endregion

        #region Public Variables

        public UserStory CurrentUserStory { get; set; }

        #endregion

        public CreateAcceptanceTest(UserStory userStory)
        {
           
            InitializeComponent();
            DataContext = this;
            _client = new UserStoryClient();

            CurrentPage = this.Title;
            CurrentUserStory = userStory;

            PageSetup();
        }

        #region Class Methods

        /// <summary>
        /// Set style variables on page setup
        /// </summary>
        private void PageSetup()
        {
            _invalidTextBoxStyle = FindResource("InvalidTextBox") as Style;
            _validTextBoxStyle = FindResource("DefaultTextBox") as Style;
        }

        /// <summary>
        /// Performs a check to ensure that no fields are left
        /// blank when creating an acceptance test
        /// </summary>
        private bool CheckFields()
        {
            bool valid = true;
            StringBuilder sb = new StringBuilder();

            if (!String.IsNullOrEmpty(GivenTextBox.Text))
            {
                GivenTextBox.Style = _validTextBoxStyle;
            }
            else
            {
                GivenTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a 'Given' for Acceptance Test\n");
            }

            if (!String.IsNullOrEmpty(WhenTextBox.Text))
            {
                WhenTextBox.Style = _validTextBoxStyle;
            }
            else
            {
                WhenTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a 'When' for Acceptance Test\n");
            }

            if (!String.IsNullOrEmpty(ThenTextBox.Text))
            {
                ThenTextBox.Style = _validTextBoxStyle;
            }
            else
            {
                ThenTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a 'Then' for Acceptance Test");
            }

            _warningMessage = sb.ToString();
            return valid;
        }
        #endregion

        #region Command Methods

        public ICommand CancelCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page userStoryDetail = new UserStoryDetails(CurrentUserStory);

                    NavigationService.GetNavigationService(this).Navigate(userStoryDetail);
                });
            }
        }

        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (CheckFields())
                    {
                        AcceptanceTest acceptanceTest = new AcceptanceTest(GivenTextBox.Text, WhenTextBox.Text, ThenTextBox.Text, CurrentUserStory);

                        try
                        {
                            _client.CreateAcceptanceTest(acceptanceTest);

                            MessageBoxUtil.ShowSuccessBox("Acceptance Test Creation Successful!");

                            Page userStoryDetails = new UserStoryDetails(CurrentUserStory);

                            NavigationService.GetNavigationService(this).Navigate(userStoryDetails);
                        }
                        catch (RestResponseErrorException ex)
                        {
                            MessageBoxUtil.ShowErrorBox(ex.Message);
                        }
                    }
                    else
                    {
                        MessageBoxUtil.ShowWarningBox(_warningMessage);
                    }
                });
            }
        }
        
        #endregion
    }
}
