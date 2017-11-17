﻿using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for CreateUserStory.xaml
    /// </summary>
    public partial class CreateUserStory : Page
    {
        #region Private Variables

        private UserStoryClient _client;

        private Project _currentProject;

        #endregion

        #region Public Variables

        public String NameLabel { get; set; } = "Story Name";

        public String MarketValueLabel { get; set; } = "Market Value";

        public String DescriptionLabel { get; set; } = "Description";

        public String NameTextContent { get; set; }

        public String MarketValueTextContent { get; set; }

        public String DescriptionTextContent { get; set; }

        public String CancelButtonText { get; set; } = "Cancel";

        public String CreateButtonText { get; set; } = "Create";

        #endregion

        public CreateUserStory(Project project)
        {
            InitializeComponent();
            DataContext = this;
            _client = new UserStoryClient();

            _currentProject = project;
        }

        #region Command Methods

        /// <summary>
        /// Cancels the Story Creation and returns the User to the previous page
        /// </summary>
        public ICommand CancelCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page productBacklogPage = new ProductBacklog(_currentProject);

                    NavigationService.GetNavigationService(this).Navigate(productBacklogPage);
                });
            }
        }

        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    UserStory userStory = new UserStory(
                            NameTextContent,
                            DescriptionTextContent,
                            int.Parse(MarketValueTextContent),
                            _currentProject);

                    try
                    {
                        _client.CreateUserStory(userStory);

                        CancelCommand.Execute(null);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }

        #endregion

        #region Private Methods

        /// <summary>
        /// Performs Regex Validation live on the TextBox's as data is entered
        /// Ensures that only digits can be entered into the TextBox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void NumberOnlyTextBoxValidation(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }

        #endregion
    }
}
